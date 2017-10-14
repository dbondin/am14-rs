package ru.applmath.rmicalc.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	public static void main(String[] args) throws Throwable {
		
		Registry r = null;
		try {
			r = LocateRegistry.createRegistry(1099);
		}
		catch(Throwable t) {
			r = LocateRegistry.getRegistry(1099);
		}
		if(r == null) {
			System.err.println("Error getting registry instance");
			System.exit(1);
		}
		
		RmiCalcEntryImpl calc = new RmiCalcEntryImpl();
		
		r.rebind("rmi-calc-1", calc);
		
		System.out.println("Started");
	}
}
