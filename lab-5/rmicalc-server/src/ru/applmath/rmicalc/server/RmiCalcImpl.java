package ru.applmath.rmicalc.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.IstringHelper;

import ru.applmath.rmicalc.api.DuckDuck;
import ru.applmath.rmicalc.api.MyOwnData;
import ru.applmath.rmicalc.api.RmiCalc;
import ru.applmath.rmicalc.api.RmiCalcListener;

public class RmiCalcImpl extends UnicastRemoteObject implements RmiCalc {

	private static final long serialVersionUID = -2115595810920547645L;

	public RmiCalcImpl() throws RemoteException {
		super();
	}

	@Override
	public double sum(double x, double y) throws RemoteException {
		handleEvent("sum");
		System.out.println("sum()");
		return x + y;
	}

	@Override
	public double div(double x, double y) throws RemoteException {
		handleEvent("div");
		System.out.println("div()");
		return x - y;
	}

	@Override
	public double sum(List<Number> list) throws RemoteException {
		handleEvent("sum(list)");
		double sum = 0.0;
		for (Number n : list) {
			sum += n.doubleValue();
		}
		return sum;
	}

	@Override
	public double process(MyOwnData mod) throws RemoteException {
		handleEvent("process");
		System.out.println(mod.getS());
		return mod.getI();
	}

	@Override
	public void duck(DuckDuck duck) throws RemoteException {
		handleEvent("duck");
		System.out.println("duck() called");
		duck.doit("Кря-кря");
	}

	private List<RmiCalcListener> listeners = new ArrayList<RmiCalcListener>();

	@Override
	public void registerListener(RmiCalcListener l) throws RemoteException {
		listeners.add(l);
	}

	public void handleEvent(String eventName) {
		for (RmiCalcListener l : listeners) {
			try {
				l.onEvent(eventName);
			} catch (Throwable t) {
				/* IGNORE */
			}
		}
	}
}
