package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dominio.entidade.Teste;
import org.acme.dominio.repository.TesteRepository;
import org.acme.interceptor.EventLog;

import java.util.List;

@Path("/teste")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TesteRest {

    @Inject
    TesteRepository testeRepository;

    @GET
    @EventLog
    public List<Teste> obterTodos() {
        return testeRepository.listAll();
    }

    @POST
    @Transactional
    public void adicionarTeste(Teste teste) {
        testeRepository.persist(teste);
    }
}
