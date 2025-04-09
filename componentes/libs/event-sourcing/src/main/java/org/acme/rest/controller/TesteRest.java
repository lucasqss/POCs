package org.acme.rest.controller;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.annotations.EventLog;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/teste")
@EventLog
public class TesteRest {

    @POST
    @Path("/teste")
    public void teste(String texto) {
        System.out.println("Texto recebido: " + texto);
    }
}