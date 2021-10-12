package br.com.unipix.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_rota_fornecedor", catalog = "unipix")
public class RotaFornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id; 
	
	@Column(name = "rota_id")
	private Long rota_id; 
	
	@Column(name = "fornecedor_id")
	private Long fornecedor_id; 
	
	@Column(name = "probabilidade")
	private Integer probabilidade;
}
