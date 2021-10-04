package br.com.unipix.api.kafka.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.unipix.api.model.SMS;

@Component
public class MessageListener {

	@Autowired
	private Gson jsonConverter;

	@KafkaListener(topics = "sms")
	public void getTb1FromKafka(String mensagem) {
		try {
			SMS sms = (SMS) jsonConverter.fromJson(mensagem, SMS.class);
			System.out.println(sms);
		} catch (Exception e) {
			System.out.println(mensagem);
		}
	}
}
