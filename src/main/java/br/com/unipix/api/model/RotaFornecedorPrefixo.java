package br.com.unipix.api.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_rota_fornecedor_prefixo", catalog = "unipix")
public class RotaFornecedorPrefixo {

    @EmbeddedId
    private RotaFornecedorPrefixoId rotaFornecedorPrefixoId;
}
