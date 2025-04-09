package org.acme.rest.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/evento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExemploRest {

    @POST
    @Path("/receber")
    public boolean receberEvento(String json) {

        System.out.println("recebendo evento: " + json);

        return true;
    }

}
