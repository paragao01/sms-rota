package br.com.unipix.api.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import br.com.unipix.api.model.SMS;
import br.com.unipix.api.service.RotaService;

@Component
public class MessageListener {

	@Value("${urlEnviaSMS}")
	private String urlEnviaSMS;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Gson jsonConverter;

	@Autowired
	private RotaService rotaService;

	@KafkaListener(topics = "sms")
	public void getSMSFromKafka(String mensagem) {
		System.out.println(mensagem);
		SMS sms = (SMS) jsonConverter.fromJson(mensagem, SMS.class);
		sms.setProdutoId(1L);
		System.out.println(sms);
		sms = rotaService.classificaFornecedorPorArea(sms);
		if (sms.getFornecedorId() != null) {
			enviarFornecedorSMS(sms);
			System.out.println("sms enviado");
		} else {
			System.out.println("sms sem rota");
		}
	}
	
	public void enviarFornecedorSMS(SMS sms) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<?> request = new HttpEntity<Object>(sms, headers);
		restTemplate.exchange(urlEnviaSMS, HttpMethod.POST, request, String.class);
	}
}
