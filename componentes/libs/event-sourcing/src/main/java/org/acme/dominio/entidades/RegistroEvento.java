package org.acme.dominio.entidades;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegistroEvento {

    //ok
    private String idRequisicao;

    //ok
    private String microsservicoOrigem;

    private String userId;

    //ok

    private String nomeMetodo;

    //ok

    private String parametros;

    //ok

    private String resposta;

    //ok

    private String excecao;

    //ok

    private Duration tempoExecucao;

    //ok

    private LocalDateTime timestamp;

}
