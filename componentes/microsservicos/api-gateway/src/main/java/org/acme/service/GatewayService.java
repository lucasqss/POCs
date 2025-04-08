package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.acme.dto.ExemploDTO;
import org.acme.rest.client.AggregatorClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped

public class GatewayService {

    @Inject
    @RestClient
    AggregatorClient aggregatorClient;

    public List<ExemploDTO> consultarTodosExemplos() {
        return aggregatorClient.consultarTodos();
    }

    public void persistirExemplo(ExemploDTO exemploDTO) {
        aggregatorClient.persistirExemplo(exemploDTO);
    }
}
