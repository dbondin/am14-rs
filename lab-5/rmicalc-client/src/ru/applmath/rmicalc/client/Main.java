package ru.applmath.rmicalc.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import ru.applmath.rmicalc.api.DuckDuck;
import ru.applmath.rmicalc.api.MyOwnData;
import ru.applmath.rmicalc.api.RmiCalc;
import ru.applmath.rmicalc.api.RmiCalcEntry;

public class Main {
	
	private static class DuckDuckImpl extends UnicastRemoteObject implements DuckDuck {
		
		private static final long serialVersionUID = -1477890812011528429L;
		
		public DuckDuckImpl() throws RemoteException {
		}
		@Override
		public void doit(String arg) throws RemoteException {
			System.out.println("Doit: " + arg);
		}
	}
	
	public static void main(String[] args) throws Throwable {
		
		if(args.length < 1) {
			System.err.println("Server name required!");
			System.exit(1);
		}
		
		RmiCalcEntry calcEntry = (RmiCalcEntry) Naming.lookup("rmi://" + args[0] + "/rmi-calc-1");
		
		RmiCalc calc = calcEntry.login("dbondin", "qwerty");
		
		System.out.println("2+2=" + calc.sum(2, 2));
		
		List<Number> list = new ArrayList<Number>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		System.out.println("1+2+3+4=" + calc.sum(list));
		
		MyOwnData mod = new MyOwnData();
		mod.setI(123);
		mod.setS("Hello");
		calc.process(mod);
		
		DuckDuck duck = new DuckDuckImpl();
		calc.duck(duck);
	}
}
