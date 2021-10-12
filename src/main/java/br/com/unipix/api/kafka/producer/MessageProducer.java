package br.com.unipix.api.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.unipix.api.model.SMS;

@Component
public class MessageProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private Gson jsonConverter;

	public void putToKafka(SMS sms) {
		kafkaTemplate.send("sms-retorno", jsonConverter.toJson(sms));
	}

}
