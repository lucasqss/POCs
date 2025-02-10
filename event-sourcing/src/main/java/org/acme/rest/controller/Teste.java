package org.acme.rest.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.acme.annotations.EventLog;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@EventLog
public class Teste {

    @GET
    @Path("/hello")
    public String obterTodos(@QueryParam("exemplo") String exemplo) {
        return "Hello "+exemplo;
    }
}
