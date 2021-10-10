package br.com.unipix.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unipix.api.model.SMS;
import br.com.unipix.api.repository.FornecedorPrefixo;
import br.com.unipix.api.repository.RotaRepository;

@Service
public class RotaService {

	@Autowired
	private RotaRepository rotaRepository;
	
	public SMS classificaFornecedorPorArea(SMS sms) {
		List<FornecedorPrefixo> fornecedores = rotaRepository.obterFornecedorPorPrefixo(sms.getProdutoId(), sms.getFornecedorId());
		if (fornecedores.size() > 0) {
			FornecedorPrefixo fornecedorPrefixo = fornecedores.stream().filter(x -> sms.getNumero().substring(2,4).equals(x.getPrefixo())).findFirst().orElse(null);
	        if (fornecedorPrefixo != null) {
	        	sms.setFornecedorId(fornecedorPrefixo.getFornecedorId());
	        	sms.setRotaId(fornecedorPrefixo.getRotaId());
	        }
		}
		System.out.println(sms);
        return sms;
	}
}