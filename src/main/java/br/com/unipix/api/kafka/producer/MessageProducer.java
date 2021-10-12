package br.com.unipix.api.kafka.producer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.unipix.api.dto.output.SMSResponse;

@Component
public class MessageProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private Gson jsonConverter;

	public void putToKafka(List<SMSResponse> sms) {
		for (SMSResponse smsResponse : sms) {
			kafkaTemplate.send("sms-retorno", jsonConverter.toJson(smsResponse));
		}
	}

	public void putToKafka(SMSResponse sms) {
		kafkaTemplate.send("sms-retorno", jsonConverter.toJson(sms));
	}

}
