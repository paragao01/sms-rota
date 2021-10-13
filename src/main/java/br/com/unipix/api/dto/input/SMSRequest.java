package br.com.unipix.api.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SMSRequest {

	@JsonProperty("produtoId")
	private Long produtoId;
	
	@JsonProperty("campanhaId")
	private String campanhaId;
	
	@JsonProperty("smsId")
	private String smsId;
	
	@JsonProperty("numero")
	private String numero;
	
	@JsonProperty("mensagem")
	private String mensagem;
	
	@JsonProperty("rotaId")
	private Long rotaId;

	@JsonProperty("fornecedorId")
	private Long fornecedorId;

}
