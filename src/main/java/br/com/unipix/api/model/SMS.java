package br.com.unipix.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SMS{

	@JsonProperty("produtoId")
	private Long produtoId;
	
	@JsonProperty("smsId")
	private String smsId;
	
	@JsonProperty("numero")
	private String numero;
	
	@JsonProperty("mensagem")
	private String mensagem;
	
}
