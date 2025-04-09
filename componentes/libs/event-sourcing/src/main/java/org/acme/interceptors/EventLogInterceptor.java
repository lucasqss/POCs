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
import org.acme.rest.client.GerenciadorEventosClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;

@EventLog
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class EventLogInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogInterceptor.class);

    @Inject
    @RestClient
    GerenciadorEventosClient client;

    @Inject
    ObjectMapper objectMapper;

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        RegistroEvento logEntry = createLogEntry(context);

        try {
            Object result = context.proceed();
            logEntry.setResposta(result != null ? objectMapper.writeValueAsString(result) : "null");

            String json = objectMapper.writeValueAsString(logEntry);

            LOGGER.info("enviando evento: {}", json);
            client.enviarEvento(json);

            return result; // Retorne o resultado do método interceptado
        } catch (Exception e) {
            LOGGER.error("Erro ao processar o interceptor", e);
            throw e; // Repropague a exceção
        }
    }

//    @AroundInvoke
//    public void log(InvocationContext context) throws Exception {
//
//        RegistroEvento logEntry = createLogEntry(context);
//
//        try {
//            Object result = context.proceed();
//            logEntry.setResposta(result != null ? objectMapper.writeValueAsString(result) : "null");
//
//            String json = objectMapper.writeValueAsString(logEntry);
//
//            LOGGER.info(json);
//            client.realizarPost(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private RegistroEvento createLogEntry(InvocationContext context) {
        RegistroEvento logEntry = new RegistroEvento();
        logEntry.setNomeMetodo(context.getMethod().getName());
        logEntry.setParametros(Arrays.toString(context.getParameters()));

        // Extract the request ID from OpenTelemetry context
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        String requestId = spanContext.getTraceId();
        logEntry.setIdRequisicao(requestId);

        LOGGER.debug("entrada criada para o metodo: {}", logEntry.getNomeMetodo());
        logEntry.setTimestamp(LocalDateTime.now());

        return logEntry;
    }
}