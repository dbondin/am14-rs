package ru.applmath.amqp.currency.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CurrencyQuery implements Serializable {

	private static final long serialVersionUID = 8441047729652869197L;

	private double value;
	
	private String clientId;
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public byte [] toByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		baos.close();
		return baos.toByteArray();
	}
	
	public static CurrencyQuery fromByteArray(final byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		CurrencyQuery result = null;
		try {
			result = (CurrencyQuery) ois.readObject();
		}
		catch(ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		ois.close();
		bais.close();
		return result;
	}

}
