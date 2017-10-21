package ru.applmath.rmicalc.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiCalc extends Remote {
	double sum(List<Number> list) throws RemoteException;
	double sum(double x, double y) throws RemoteException;
	double div(double x, double y) throws RemoteException;
	double process(MyOwnData mod) throws RemoteException;
	void duck(DuckDuck duck) throws RemoteException;
	void registerListener(RmiCalcListener l) throws RemoteException;
}
