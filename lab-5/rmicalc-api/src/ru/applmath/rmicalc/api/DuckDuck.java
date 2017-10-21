package ru.applmath.rmicalc.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DuckDuck extends Remote {
	void doit(String arg) throws RemoteException;
}
