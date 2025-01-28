package org.acme.dominio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dominio.entidade.Teste;

@ApplicationScoped

public class TesteRepository implements PanacheRepository<Teste> {

}
