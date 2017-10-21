package ru.applmath.corba.messageboard.server;

import java.util.Date;

import MessageBoard.MessagePostBoxPOA;

public class MessagePostBoxImpl extends MessagePostBoxPOA {

	public String getVersion() {
		return "MessagePostBoxImpl version 0.0.1-SNAPSHOT by dbondin";
	}
	
	public void putMessage(String from, String message) {
		System.out.println("****************************");
		System.out.println(new Date());
		System.out.println("FROM: " + from);
		System.out.println(message);
	}
}
