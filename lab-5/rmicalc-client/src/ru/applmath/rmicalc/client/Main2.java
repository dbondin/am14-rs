package ru.applmath.rmicalc.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ru.applmath.rmicalc.api.RmiCalc;
import ru.applmath.rmicalc.api.RmiCalcListener;

public class Main2 {
	
	private static class XXX extends UnicastRemoteObject implements RmiCalcListener {
		
		private static final long serialVersionUID = -607682475173388254L;
		
		public XXX() throws RemoteException {
		}
		@Override
		public void onEvent(String eventName) throws RemoteException {
			System.out.println("EVENT: " + eventName);
		}
	}
	
	public static void main(String[] args) throws Throwable {
		
		if(args.length < 1) {
			System.err.println("Server name required!");
			System.exit(1);
		}
		
		RmiCalc calc = (RmiCalc) Naming.lookup("rmi://" + args[0] + "/rmi-calc-1");

		calc.registerListener(new XXX());
	}
}
