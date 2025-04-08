package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.acme.dto.ExemploDTO;
import org.acme.rest.client.PersistenceClient;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class AggregatorService {

    @Inject
    @RestClient
    PersistenceClient client;

    public List<ExemploDTO> consultarTodosExemplos() {
        return client.consultarTodos();
    }

    public void persistirExemplo(ExemploDTO exemploDTO) {

        if (ObjectUtils.isNotEmpty(exemploDTO.id())) {
            throw new IllegalArgumentException("O ID não pode ser informado.");
        }

        client.persistirExemplo(exemploDTO);
    }
}
