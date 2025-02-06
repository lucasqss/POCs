package org.acme.dominio.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import org.acme.dominio.entidade.Exemplo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
class ExemploRepositoryTest {

    @Inject
    ExemploRepository exemploRepository;

    @Test
    void testObterTodos() {
        List<Exemplo> result = exemploRepository.listAll();
        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    void testAdicionarTeste() {
        Exemplo mockExemplo = new Exemplo();
        mockExemplo.setNome("Teste Unitario");
        var testeTemp = exemploRepository.getEntityManager().merge(mockExemplo);
        exemploRepository.persist(testeTemp);

        Exemplo persistedExemplo = exemploRepository.findById(testeTemp.getId());
        assertNotNull(persistedExemplo);
        assertEquals(testeTemp.getNome(), persistedExemplo.getNome());
        assertEquals(5, exemploRepository.listAll().size());

        //Voltando a base
        exemploRepository.delete(persistedExemplo);
        assertNull(exemploRepository.findById(testeTemp.getId()));
        assertEquals(4, exemploRepository.listAll().size());

    }
}