package org.acme.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "${TABELA_REGISTRO_EVENTO}", schema = "${SCHEMA_REGISTRO_EVENTO}")
public class RegistroEvento {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private String userId;

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

    @Column(name = "TS_RQSC")
    private LocalDateTime timestamp;

}
