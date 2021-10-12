package br.com.unipix.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_plano_numeracao", schema = "unipix")
public class PlanoNumeracao {
	
	@Id
	private String prefixo;
	private String tipo; 
	private String operadora;
}
