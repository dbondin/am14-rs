package ru.applmath.rmicalc.api;

import java.io.Serializable;

public class MyOwnData implements Serializable {
	
	private static final long serialVersionUID = -113867365321748730L;
	
	private int i;
	private String s;
	
	public MyOwnData() {
	}
	
	public int getI() {
		return i;
	}
	
	public void setI(int i) {
		this.i = i;
	}
	
	public String getS() {
		return s;
	}
	
	public void setS(String s) {
		this.s = s;
	}
}
