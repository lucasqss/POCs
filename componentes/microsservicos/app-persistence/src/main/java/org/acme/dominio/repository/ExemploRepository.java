package org.acme.dominio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.annotations.LogarEventos;
import org.acme.dominio.entidade.Exemplo;

@ApplicationScoped
@LogarEventos
public class ExemploRepository implements PanacheRepository<Exemplo> {
}
