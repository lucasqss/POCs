package org.acme.rest.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(configKey = "gerenciador-eventos")
@RegisterClientHeaders
public interface GerenciadorEventosClient {

    @POST
    @Path("/receber-evento")
    void enviarEvento(String json);

}
