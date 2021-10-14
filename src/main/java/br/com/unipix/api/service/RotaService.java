package br.com.unipix.api.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.com.unipix.api.dto.input.SMSRequest;
import br.com.unipix.api.dto.output.SMSResponse;
import br.com.unipix.api.kafka.producer.MessageProducer;
import br.com.unipix.api.model.PlanoNumeracao;
import br.com.unipix.api.repository.FornecedorPrefixo;
import br.com.unipix.api.repository.PlanoNumeracaoRepository;
import br.com.unipix.api.repository.RotaRepository;

@Service
public class RotaService {

	@Value("${urlEnviaSMS}")
	private String urlEnviaSMS;
	
	@Autowired
	private MessageProducer messageProducer;
	
	@Autowired
	private RotaRepository rotaRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PlanoNumeracaoRepository planoNumeracaoRepository;
	
	@Autowired
	private Gson jsonConverter;

	public SMSRequest processarSMS(SMSRequest sms) {
		sms = classificaFornecedor(sms);
		if (sms.getFornecedorId() != null) {
			try {
				List<SMSResponse> lista = enviarFornecedorSMS(sms);
				messageProducer.putToKafka(lista);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("erro");
			}
		} else {
			System.out.println("sms sem rota");
		}
		return sms;
	}
	
	public SMSRequest classificaFornecedor(SMSRequest sms) {
		List<FornecedorPrefixo> lista = obterConfiguracoesEncaminhamento(sms);
		if (lista.size() > 0) {
        	sms.setFornecedorId(lista.get(lista.size()-1).getFornecedorId());
        	sms.setRotaId(lista.get(lista.size()-1).getRotaId());
		}
        return sms;
	}
	
	private List<FornecedorPrefixo> obterConfiguracoesEncaminhamento(SMSRequest sms) {
		List<FornecedorPrefixo> lista = new ArrayList<>();
		List<FornecedorPrefixo> fornecedores = rotaRepository.obterFornecedorPorPrefixo(sms.getProdutoId(), sms.getFornecedorId());
		if (fornecedores.size() > 0) {
			String operadora = buscaOperadora(sms.getNumero());
			List<FornecedorPrefixo> fornecedorPrefixo = fornecedores.stream().filter(x -> sms.getNumero().substring(2,4).equals(x.getPrefixo()) && x.getOperadora().equals("")).collect(Collectors.toList());
			List<FornecedorPrefixo> fornecedorOperadora = fornecedores.stream().filter(x -> operadora.equals(x.getOperadora()) && x.getPrefixo().equals("")).collect(Collectors.toList());
			List<FornecedorPrefixo> fornecedorPrefixoOperadora = fornecedores.stream().filter(x -> sms.getNumero().substring(2,4).equals(x.getPrefixo()) && operadora.equals(x.getOperadora())).collect(Collectors.toList());
			lista.addAll(fornecedorPrefixo);
			lista.addAll(fornecedorOperadora);
			lista.addAll(fornecedorPrefixoOperadora);
			lista.sort(Comparator.comparing(FornecedorPrefixo::getPrioridade));
		}
		for (FornecedorPrefixo fornecedorPrefixo : lista) {
			System.out.println(" operadora "+fornecedorPrefixo.getOperadora());
			System.out.println(" prefixo "+fornecedorPrefixo.getPrefixo());
			System.out.println(" operadora prefixo"+fornecedorPrefixo.getOperadora()+" "+fornecedorPrefixo.getPrefixo());
		}
		return lista;
	}
	
	public List<SMSResponse> enviarFornecedorSMS(SMSRequest sms) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<?> request = new HttpEntity<Object>(sms, headers);
		ResponseEntity<String> responses = restTemplate.exchange(urlEnviaSMS, HttpMethod.POST, request, String.class);
		Type listType = new TypeToken<ArrayList<SMSResponse>>(){}.getType();
		List<SMSResponse> list = jsonConverter.fromJson(responses.getBody(), listType);  
		return list;
	}

	private String buscaOperadora(String numeroDiscado) {
		try {
			String prefixo = null;
			if (numeroDiscado.length() == 13) {
				prefixo = numeroDiscado.substring(0, 9);
			} else {
				prefixo = numeroDiscado.substring(0, 8);
			}
			Optional<PlanoNumeracao> entityC = planoNumeracaoRepository.findById(prefixo);
			if (entityC.isPresent()) {
				return entityC.get().getOperadora().toLowerCase();
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println(numeroDiscado);
		}
		return null;
	}

}