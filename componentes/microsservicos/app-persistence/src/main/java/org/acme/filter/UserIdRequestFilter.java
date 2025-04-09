package org.acme.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.quarkus.logging.Log;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.acme.utils.OpenTelemetryUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Provider
public class UserIdRequestFilter implements ContainerRequestFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Tracer tracer = io.opentelemetry.api.GlobalOpenTelemetry.getTracer("UserIdRequestFilter");

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            // Lê o corpo da requisição
            InputStream inputStream = requestContext.getEntityStream();
            String body = new String(inputStream.readAllBytes());

            // Parseia o JSON para extrair o atributo "codigoCliente"
            JsonNode jsonNode = objectMapper.readTree(body);
            JsonNode codigoClienteNode = jsonNode.get("codigoCliente");

            if (codigoClienteNode != null && !codigoClienteNode.asText().isEmpty()) {
                String codigoCliente = codigoClienteNode.asText();

                // Verifica se há um Span ativo
                Span currentSpan = Span.current();
                if (currentSpan == null || !currentSpan.getSpanContext().isValid()) {
                    // Cria um novo Span se não houver um ativo
                    currentSpan = tracer.spanBuilder("UserIdRequestFilter").startSpan();
                }

                // Adiciona o codigoCliente ao Span atual
                try (var scope = currentSpan.makeCurrent()) {
                    OpenTelemetryUtils.setUserId(codigoCliente);
                }
            } else {
                Log.info("Atributo 'codigoCliente' está ausente ou vazio no corpo da requisição");
            }

            // Reescreve o corpo da requisição para que ele possa ser lido novamente
            requestContext.setEntityStream(new ByteArrayInputStream(body.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o corpo da requisição", e);
        }
    }
}