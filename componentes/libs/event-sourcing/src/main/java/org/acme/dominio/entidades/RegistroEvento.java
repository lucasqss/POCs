package org.acme.dominio.entidades;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class RegistroEvento {

    private String idRequisicao;

    private String microsservicoOrigem;

    //TODO: terminar de testar pegando esse cara do header
    private String userId;

    private String nomeMetodo;

    @JsonRawValue
    private String entrada;

    @JsonRawValue
    private String resposta;

    private String excecao;

    private Duration tempoExecucao;

    private LocalDateTime timestamp;

}
