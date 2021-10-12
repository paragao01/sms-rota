package br.com.unipix.api.dto.output;

import lombok.Data;

@Data
public class SMSOutputDto {
	private String smsId;
    private String numero;
    private String mensagem;
    private Long campanhaId;
    private Long produtoId;
    private Long rotaId;
    private Long fornecedorId;
    private String statusFornecedor;
    private String status;
}
