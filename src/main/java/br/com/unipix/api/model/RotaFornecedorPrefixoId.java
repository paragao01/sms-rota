package br.com.unipix.api.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class RotaFornecedorPrefixoId {

	private Integer tb_rota_fornecedor_id; 
	private String prefixo;
}
