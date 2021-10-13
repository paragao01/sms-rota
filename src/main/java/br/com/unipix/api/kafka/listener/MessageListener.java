package br.com.unipix.api.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.unipix.api.dto.input.SMSRequest;
import br.com.unipix.api.service.RotaService;

@Component
public class MessageListener {

	@Autowired
	private Gson jsonConverter;

	@Autowired
	private RotaService rotaService;

	@KafkaListener(topics = "sms")
	public void getFromKafka(String mensagem) {
		System.out.println(mensagem);
		SMSRequest sms = (SMSRequest) jsonConverter.fromJson(mensagem, SMSRequest.class);
		sms.setProdutoId(1L);
		System.out.println(sms);
		sms = rotaService.processarSMS(sms);
	}
}
