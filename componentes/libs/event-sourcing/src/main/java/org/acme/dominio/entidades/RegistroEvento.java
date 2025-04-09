package org.acme.dominio.entidades;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroEvento {

    private String idRequisicao;

    private String userId;

    private String nomeMetodo;

    private String parametros;

    private String resposta;

    private String excecao;

    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return super.toString();
    }
}
