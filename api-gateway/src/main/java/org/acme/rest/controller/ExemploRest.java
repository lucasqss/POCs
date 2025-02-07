package org.acme.rest.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dominio.entidade.Teste;
import org.acme.dominio.repository.TesteRepository;
import org.acme.dto.ExemploDTO;
import org.acme.interceptor.EventLog;

import java.util.List;

@Path("/teste")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExemploRest {



    @GET
    public List<ExemploDTO> obterTodos() {
        return testeRepository.listAll();
    }

    @POST
    public void adicionarTeste(ExemploDTO exemploDTO) {
        testeRepository.persist(teste);
    }
}
