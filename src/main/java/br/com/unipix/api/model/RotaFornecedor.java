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
@Table(name = "tb_rota_fornecedor", catalog = "SMSHub")
public class RotaFornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id; 
	
	@Column(name = "rota_id")
	private Integer rota_id; 
	
	@Column(name = "fornecedor_id")
	private Integer fornecedor_id; 
	
	@Column(name = "probabilidade")
	private Integer probabilidade;
}