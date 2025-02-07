package org.acme.rest.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;
import org.acme.dto.ExemploDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "aggregator")
@RegisterClientHeaders
public interface AggregatorClient {

    @GET
    @Path("/aggregator/consultar")
    List<ExemploDTO> consultarTodos();

    @POST
    @Path("/aggregator/persistir")
    void persistirExemplo(ExemploDTO exemploDTO);
}
