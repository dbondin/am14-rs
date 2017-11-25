package ru.applmath.amqp.currency.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import static ru.applmath.amqp.currency.common.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.applmath.amqp.currency.common.BrokerMessage;
import ru.applmath.amqp.currency.common.CurrencyAnswer;
import ru.applmath.amqp.currency.common.CurrencyQuery;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

public class CurrencyConverter {

	private static Logger LOGGER = LoggerFactory
			.getLogger(CurrencyConverter.class);

	private double exchangeRate = 1.0;
	
	private double doConversion(final double value) {
		return exchangeRate * value;
	}
	
	public void start() throws Throwable {
		LOGGER.info("starting");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://test:test@localhost:5672/");
		// TODO: Тут бага в библиотеке !!!
		factory.setVirtualHost("/");
		Connection conn = factory.newConnection();

		LOGGER.info("connected to rabbit");

		Channel channel = conn.createChannel();

		channel.exchangeDeclare(CURRENCY_QUERY_EXCHANGE_NAME, "direct", false);
		LOGGER.info("exchange {} declared", CURRENCY_QUERY_EXCHANGE_NAME);

		channel.queueDeclare(CURRENCY_QUERY_QUEUE_NAME, false, false, false,
				null);
		LOGGER.info("queue {} declared", CURRENCY_QUERY_QUEUE_NAME);

		channel.queueBind(CURRENCY_QUERY_QUEUE_NAME,
				CURRENCY_QUERY_EXCHANGE_NAME, "");
		LOGGER.info("queue {} binded to {}", CURRENCY_QUERY_QUEUE_NAME,
				CURRENCY_QUERY_EXCHANGE_NAME);

		channel.exchangeDeclare(CURRENCY_BROKER_EXCHANGE_NAME, "direct", false);
		LOGGER.info("exchange {} declared", CURRENCY_QUERY_EXCHANGE_NAME);

		String myBrokerQueueName = channel.queueDeclare().getQueue();
		channel.queueBind(myBrokerQueueName, CURRENCY_BROKER_EXCHANGE_NAME, "");
		
		channel.basicConsume(CURRENCY_QUERY_QUEUE_NAME, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				getChannel().basicAck(envelope.getDeliveryTag(), false);
				LOGGER.info("handleDelivery {} {} {}", envelope, properties, body);
				
				CurrencyQuery cq = CurrencyQuery.fromByteArray(body);
				
				LOGGER.info("value={}", cq.getValue());
				
				double result = doConversion(cq.getValue());
				
				CurrencyAnswer ca = new CurrencyAnswer();
				ca.setValue(result);
				
				BasicProperties bp = new BasicProperties.Builder().contentType(
						"application/octet-stream").build();
				getChannel().basicPublish(CURRENCY_ANSWER_EXCHANGE_NAME,
						cq.getClientId(), bp, ca.toByteArray());
			}
		});
		
		channel.basicConsume(myBrokerQueueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				BrokerMessage bm = BrokerMessage.fromByteArray(body);
				CurrencyConverter.this.exchangeRate = bm.getExchangeRate();
				LOGGER.info("new exchange rate={}", CurrencyConverter.this.exchangeRate);
			}
		});
	}

	public static void main(String[] args) {
		try {
			CurrencyConverter cc = new CurrencyConverter();
			cc.start();
		} catch (Throwable t) {
			LOGGER.error("Error", t);
			System.exit(1);
		}
	}
}
