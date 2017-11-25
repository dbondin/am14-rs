package ru.applmath.amqp.currency.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BrokerMessage implements Serializable {
	
	private static final long serialVersionUID = -3832042656624885700L;
	
	double exchangeRate;
	
	public double getExchangeRate() {
		return exchangeRate;
	}
	
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public byte [] toByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		baos.close();
		return baos.toByteArray();
	}
	
	public static BrokerMessage fromByteArray(final byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		BrokerMessage result = null;
		try {
			result = (BrokerMessage) ois.readObject();
		}
		catch(ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		ois.close();
		bais.close();
		return result;
	}

}
