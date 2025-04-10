package org.acme.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.acme.annotations.LogarEventos;
import org.acme.dto.ExemploDTO;
import org.acme.service.AggregatorService;

@Path("/aggregator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@LogarEventos
public class AggregatorRest {

    @Inject
    AggregatorService aggregatorService;

    @GET
    @Path("/consultar")
    public List<ExemploDTO> consultar() {
        return aggregatorService.consultarTodosExemplos();
    }

    @POST
    @Path("/persistir")
    public void persistir(ExemploDTO exemploDTO) {
        aggregatorService.persistirExemplo(exemploDTO);
    }

}
