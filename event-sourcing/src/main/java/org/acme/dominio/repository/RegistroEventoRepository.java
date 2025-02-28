package org.acme.dominio.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.narayana.jta.QuarkusTransaction;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.dominio.entidades.RegistroEvento;

@ApplicationScoped
public class RegistroEventoRepository implements PanacheRepository<RegistroEvento> {

    public void persistir(RegistroEvento entidade) {
        QuarkusTransaction.requiringNew().run(() -> this.persistAndFlush(entidade));
    }
}
