package com.cts.skilltracker.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.models.ProfileRsp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

@Component
public class AmazonRabbitMQConnect {

	private static Logger logger = LoggerFactory.getLogger(AmazonRabbitMQConnect.class);

	private static final boolean ACKNOWLEDGE_MODE = true;

	@Value("${spring.rabbitmq.host}")
	private String endpoint;

	@Value("${spring.rabbitmq.username}")
	private String userName;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${spring.rabbitmq.queue}")
	private String queue;

	public byte[] convertObjectToBytes(Object obj) throws IOException {
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
			ois.writeObject(obj);
			return boas.toByteArray();
		}
	}

	public String convertObjectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(obj);
		return jsonStr;
	}

	public void sendToAwsMQ(ProfileRsp user) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		logger.info("ENDPOINT:" + endpoint);
		connectionFactory.setUri(endpoint);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);

		Connection producerConnection = connectionFactory.newConnection();
		Channel producerChannel = producerConnection.createChannel();
		producerChannel.queueDeclare(queue, false, false, false, null);

		producerChannel.basicPublish("", queue, null, convertObjectToJson(user).getBytes(StandardCharsets.UTF_8));
		logger.info("Message sent: " + user);

		producerChannel.close();
		producerConnection.close();
	}

	public void receiveFromAwsMQ(ProfileRsp user) throws Exception {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setUri(endpoint);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);

		Connection consumerConnection = connectionFactory.newConnection();
		Channel consumerChannel = consumerConnection.createChannel();

		consumerChannel.queueDeclare(queue, false, false, false, null);

		GetResponse response = consumerChannel.basicGet(queue, ACKNOWLEDGE_MODE);
		String message = new String(response.getBody(), StandardCharsets.UTF_8);
		logger.info("Message received: " + message);

		consumerChannel.close();
		consumerConnection.close();
	}
}
