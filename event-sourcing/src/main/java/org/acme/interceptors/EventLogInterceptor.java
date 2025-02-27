package org.acme.interceptors;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.acme.annotations.EventLog;
import org.acme.entidades.RegistroEvento;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@EventLog
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class EventLogInterceptor {

    private static final Logger LOGGER = Logger.getLogger(EventLogInterceptor.class);

    @PersistenceContext
    private EntityManager entityManager;

    @AroundInvoke
    @Transactional
    public Object log(InvocationContext context) throws Exception {
        RegistroEvento logEntry = new RegistroEvento();
        logEntry.setNomeMetodo(context.getMethod().getName());
        logEntry.setParametros(Arrays.toString(context.getParameters()));

        // Extract the request ID from OpenTelemetry context
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        String requestId = spanContext.getTraceId();
        logEntry.setIdRequisicao(requestId);

        try {
            Object result = context.proceed();
            logEntry.setResposta(result != null ? result.toString() : "null");
            persistLogEntryAsync(logEntry);
            return result;
        } catch (Exception e) {
            logEntry.setExcecao(e.getMessage());
            persistLogEntryAsync(logEntry);
            throw e;
        }
    }

    private void persistLogEntryAsync(RegistroEvento logEntry) {
        CompletableFuture.runAsync(() -> {
            entityManager.persist(logEntry);
        });
    }
}