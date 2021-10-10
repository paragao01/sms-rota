package br.com.unipix.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class RotaFornecedorPrefixoId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long tb_rota_fornecedor_id; 
	private String prefixo;
}
