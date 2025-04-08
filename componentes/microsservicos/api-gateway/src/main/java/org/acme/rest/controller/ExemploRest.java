package org.acme.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.acme.dto.ExemploDTO;
import org.acme.service.GatewayService;

@Path("/teste")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExemploRest {

    @Inject
    GatewayService service;

    @GET
    public List<ExemploDTO> obterTodos() {
        return service.consultarTodosExemplos();
    }

    @POST
    public void adicionarTeste(ExemploDTO exemploDTO) {
        service.persistirExemplo(exemploDTO);
    }
}
