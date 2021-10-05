package br.com.unipix.api.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.unipix.api.model.SMS;

@Component
public class MessageListener {

	@Value("${urlEnviaSMS}")
	private String urlEnviaSMS;

	@Autowired
	private RestTemplate restTemplate;

	@KafkaListener(topics = "sms")
	public void getSMSFromKafka(String mensagem) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String stringCortada = mensagem.substring(1, mensagem.length()-1);  
			stringCortada =  stringCortada.replace("\\n", "").replace("\\t", "").replace("\\", "");
			SMS sms = mapper.readValue(stringCortada, SMS.class);
			System.out.println(sms);
			sms.setFornecedorId(1);
			enviarSMS(sms);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarSMS(SMS sms) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<?> request = new HttpEntity<Object>(sms, headers);
		restTemplate.exchange(urlEnviaSMS, HttpMethod.POST, request, String.class);
	}
}
