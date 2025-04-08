package org.acme.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.acme.annotations.EventLog;
import org.acme.dominio.entidades.RegistroEvento;
import org.acme.dominio.repository.RegistroEventoRepository;
import org.jboss.logging.Logger;

import java.util.Arrays;

@EventLog
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class EventLogInterceptor {

    private static final Logger LOGGER = Logger.getLogger(EventLogInterceptor.class);

    @Inject
    RegistroEventoRepository repository;

    @Inject
    ObjectMapper objectMapper;

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        RegistroEvento logEntry = createLogEntry(context);

        try {
            Object result = context.proceed();
            logEntry.setResposta(result != null ? objectMapper.writeValueAsString(result) : "null");
            repository.persistir(logEntry);
            return result;
        } catch (Exception e) {
            logEntry.setExcecao(e.getMessage());
            repository.persistir(logEntry);
            throw e;
        }
    }

    private RegistroEvento createLogEntry(InvocationContext context) {
        RegistroEvento logEntry = new RegistroEvento();
        logEntry.setNomeMetodo(context.getMethod().getName());
        logEntry.setParametros(Arrays.toString(context.getParameters()));

        // Extract the request ID from OpenTelemetry context
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        String requestId = spanContext.getTraceId();
        logEntry.setIdRequisicao(requestId);

        LOGGER.log(Logger.Level.INFO, logEntry.toString());
        return logEntry;
    }
}