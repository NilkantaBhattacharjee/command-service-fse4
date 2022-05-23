package com.cts.skilltracker.helpers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.skilltracker.config.RabbitMQProperties;
import com.cts.skilltracker.models.ProfileRsp;

@Component
public class RabbitMQSender {
	
	@Autowired
	RabbitMQProperties rmqProp;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public void send(ProfileRsp user){
        rabbitTemplate.convertAndSend(rmqProp.getExchange(),rmqProp.getRoutingKey(), user);

    }

}
