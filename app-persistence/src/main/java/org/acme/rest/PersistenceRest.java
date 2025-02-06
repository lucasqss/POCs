package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.acme.dominio.entidade.Exemplo;
import org.acme.dominio.repository.ExemploRepository;

@Path("/registro")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersistenceRest {

    @Inject
    ExemploRepository exemploRepository;

    @GET
    @Path("/consultar")
    public List<Exemplo> obterTodos() {
        return exemploRepository.listAll();
    }

    @POST
    @Transactional
    @Path("/persistir")
    public void adicionarTeste(Exemplo exemplo) {
        exemploRepository.persist(exemplo);
    }
}
