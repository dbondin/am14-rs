package ru.applmath.amqp.currency.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CurrencyAnswer implements Serializable {

	private static final long serialVersionUID = -3832042676626885600L;

	private double value;
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public byte [] toByteArray() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		baos.close();
		return baos.toByteArray();
	}
	
	public static CurrencyAnswer fromByteArray(final byte[] data) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		CurrencyAnswer result = null;
		try {
			result = (CurrencyAnswer) ois.readObject();
		}
		catch(ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		ois.close();
		bais.close();
		return result;
	}
}
