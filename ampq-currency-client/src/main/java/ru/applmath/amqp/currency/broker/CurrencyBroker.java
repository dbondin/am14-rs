package ru.applmath.amqp.currency.broker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.applmath.amqp.currency.common.BrokerMessage;
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

public class CurrencyBroker {

	private static Logger LOGGER = LoggerFactory
			.getLogger(CurrencyBroker.class);

	public void start() throws Throwable {
		LOGGER.info("starting");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://test:test@localhost:5672/");
		// TODO: Тут бага в библиотеке !!!
		factory.setVirtualHost("/");
		Connection conn = factory.newConnection();

		LOGGER.info("connected to rabbit");

		Channel channel = conn.createChannel();

		channel.exchangeDeclare(CURRENCY_BROKER_EXCHANGE_NAME, "direct", false);
		LOGGER.info("exchange {} declared", CURRENCY_QUERY_EXCHANGE_NAME);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter exchange rate: ");
			double usd = scanner.nextDouble();

			BrokerMessage bm = new BrokerMessage();
			bm.setExchangeRate(usd);
			
			BasicProperties bp = new BasicProperties.Builder().contentType(
					"application/octet-stream").build();
			channel.basicPublish(CURRENCY_BROKER_EXCHANGE_NAME, "", bp, bm.toByteArray());
		}

		// channel.close();
		// conn.close();
	}

	public static void main(String[] args) {
		try {
			CurrencyBroker cc = new CurrencyBroker();
			cc.start();
		} catch (Throwable t) {
			LOGGER.error("Error", t);
			System.exit(1);
		}
	}
}
