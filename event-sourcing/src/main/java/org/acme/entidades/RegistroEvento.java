package org.acme.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "${TABELA_REGISTRO_EVENTO}")
public class RegistroEvento {

    @Id
    @Column(name = "ID", updatable = false)
    private Long id;

    @Column(name = "ID_RQSC")
    private String idRequisicao;

    @Column(name = "NM_MTD")
    private String nomeMetodo;

    @Column(name = "PRM")
    private String parametros;

    @Column(name = "RPST")
    private String resposta;

    @Column(name = "EXCO")
    private String excecao;
}
