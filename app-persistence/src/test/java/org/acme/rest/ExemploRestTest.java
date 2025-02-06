package org.acme.rest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import org.acme.dominio.entidade.Exemplo;
import org.acme.dominio.repository.ExemploRepository;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
class ExemploRestTest {

    @Inject
    ExemploRepository exemploRepository;

    private static final String URL_CONSULTAR = "/registro/consultar";
    private static final String URL_PERSISTIR = "/registro/persistir";

    @Test
    void obterTodos() {
        Exemplo[] array = given()
                .when()
                .get(URL_CONSULTAR)
                .then()
                .statusCode(200)
                .extract()
                .as(Exemplo[].class);
        assertEquals(4, array.length);
    }

    @Test
    @Transactional
    void adicionarTeste() {

        String jsonBody = "{ \"nome\": \"testeUnitario\"}";

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post(URL_PERSISTIR)
                .then()
                .statusCode(204);

        List<Exemplo> resultList = exemploRepository.listAll();
        assertEquals(5, resultList.size());

        //Voltando a base
        Exemplo exemplo = exemploRepository.find("nome", "testeUnitario").firstResult();
        exemplo = exemploRepository.getEntityManager().merge(exemplo);
        exemploRepository.delete(exemplo);
        assertNull(exemploRepository.findById(exemplo.getId()));
        assertEquals(4, exemploRepository.listAll().size());

    }
}