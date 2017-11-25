package ru.applmath.amqp.currency.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.applmath.amqp.currency.common.Constants;
import ru.applmath.amqp.currency.common.CurrencyAnswer;
import ru.applmath.amqp.currency.common.CurrencyQuery;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import static ru.applmath.amqp.currency.common.Constants.*;

public class CurrencyClient {

	private static Logger LOGGER = LoggerFactory
			.getLogger(CurrencyClient.class);

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

		channel.exchangeDeclare(CURRENCY_ANSWER_EXCHANGE_NAME, "direct", false);
		LOGGER.info("exchange {} declared", CURRENCY_ANSWER_EXCHANGE_NAME);

		String myAnswerQueueName = channel.queueDeclare().getQueue();
		LOGGER.info("queue {} declared", myAnswerQueueName);
		
		channel.queueBind(myAnswerQueueName, CURRENCY_ANSWER_EXCHANGE_NAME, myAnswerQueueName);
		
		channel.basicConsume(myAnswerQueueName, true, new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope,
					BasicProperties properties, byte[] body) throws IOException {
				CurrencyAnswer ca = CurrencyAnswer.fromByteArray(body);
				System.out.println("Result: " + ca.getValue());
			}
		});
		
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter ammount: ");
			double usd = scanner.nextDouble();

			CurrencyQuery cq = new CurrencyQuery();
			cq.setValue(usd);
			cq.setClientId(myAnswerQueueName);
			
			BasicProperties bp = new BasicProperties.Builder().contentType(
					"application/octet-stream").build();
			channel.basicPublish(CURRENCY_QUERY_EXCHANGE_NAME, "", bp, cq.toByteArray());
		}

		// channel.close();
		// conn.close();
	}

	public static void main(String[] args) {
		try {
			CurrencyClient cc = new CurrencyClient();
			cc.start();
		} catch (Throwable t) {
			LOGGER.error("Error", t);
			System.exit(1);
		}
	}
}
