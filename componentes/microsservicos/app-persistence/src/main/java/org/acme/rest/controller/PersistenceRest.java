package org.acme.rest.controller;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.annotations.EventLog;
import org.acme.dominio.entidade.Exemplo;
import org.acme.dominio.repository.ExemploRepository;

import java.util.List;

@Path("/registro")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@EventLog
public class PersistenceRest {

    @Inject
    ExemploRepository exemploRepository;

    @GET
    @Path("/consultar")
    @EventLog
    public List<Exemplo> obterTodos() {
        return exemploRepository.listAll();
    }

    @POST
    @Transactional
    @Path("/persistir")
    @EventLog
    public void adicionarTeste(Exemplo exemplo) {
        exemploRepository.persist(exemplo);
    }
}
