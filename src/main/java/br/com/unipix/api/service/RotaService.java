package br.com.unipix.api.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
import br.com.unipix.api.repository.FornecedorPrefixo;
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
		List<FornecedorPrefixo> fornecedores = rotaRepository.obterFornecedorPorPrefixo(sms.getProdutoId(), sms.getFornecedorId());
		if (fornecedores.size() > 0) {
			FornecedorPrefixo fornecedorPrefixo = fornecedores.stream().filter(x -> sms.getNumero().substring(2,4).equals(x.getPrefixo())).findFirst().orElse(null);
	        if (fornecedorPrefixo != null) {
	        	sms.setFornecedorId(fornecedorPrefixo.getFornecedorId());
	        	sms.setRotaId(fornecedorPrefixo.getRotaId());
	        }
		}
        return sms;
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

}