package org.acme.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opentelemetry.api.trace.Span;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.acme.annotations.LogarEventos;
import org.acme.dominio.entidades.RegistroEvento;
import org.acme.rest.client.GerenciadorEventosClient;
import org.acme.utils.OpenTelemetryUtils;
import org.acme.utils.PomUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;

@LogarEventos
@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class EventLogInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogInterceptor.class);

    @Inject
    @RestClient
    GerenciadorEventosClient client;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(com.fasterxml.jackson.core.JsonGenerator.Feature.ESCAPE_NON_ASCII, false);

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {

        LocalDateTime inicio = LocalDateTime.now();

        try {
            Object result = context.proceed();
            LocalDateTime fim = LocalDateTime.now();

            RegistroEvento registroEvento = montarRegistroEventoSucesso(context, result, Duration.between(inicio, fim));

            String json = objectMapper.writeValueAsString(registroEvento);

            LOGGER.info("\n\nenviando evento: {} \n\n", json);
            client.enviarEvento(json);

            return result; // Retorne o resultado do método interceptado
        } catch (Exception e) {
            LOGGER.error("Erro ao processar o interceptor", e);
            RegistroEvento registroEvento = montarRegistroEventoErro(context, e);
            String json = objectMapper.writeValueAsString(registroEvento);

            client.enviarEvento(json);
            throw e; // Repropague a exceção
        }
    }

    private RegistroEvento montarRegistroEventoSucesso(InvocationContext context, Object result, Duration tempoExecucao) throws Exception {

        RegistroEvento registroEvento = new RegistroEvento();

        registroEvento.setMicrosservicoOrigem(PomUtils.getArtifactId());
        registroEvento.setIdRequisicao(Span.current().getSpanContext().getTraceId());
        registroEvento.setNomeMetodo(context.getMethod().getDeclaringClass().getName() + "." + context.getMethod().getName());
        registroEvento.setEntrada(objectMapper.writeValueAsString(context.getParameters()));
        registroEvento.setTimestamp(LocalDateTime.now());
        registroEvento.setResposta(result != null ? objectMapper.writeValueAsString(result) : "null");
        registroEvento.setTempoExecucao(tempoExecucao);

        return registroEvento;
    }

    private RegistroEvento montarRegistroEventoErro(InvocationContext context, Exception exception) throws Exception {

        RegistroEvento registroEvento = new RegistroEvento();

        registroEvento.setUserId(OpenTelemetryUtils.getUserId());
        registroEvento.setIdRequisicao(Span.current().getSpanContext().getTraceId());
        registroEvento.setNomeMetodo(context.getMethod().getName());
        registroEvento.setEntrada(objectMapper.writeValueAsString(context.getParameters()));
        registroEvento.setTimestamp(LocalDateTime.now());

        // Captura o stack trace completo como String
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        String fullStackTrace = stringWriter.toString();

        // Serializa o stack trace completo
        registroEvento.setExcecao(objectMapper.writeValueAsString(fullStackTrace));

        return registroEvento;
    }

}