package ru.applmath.rmicalc.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiCalcListener extends Remote {
	void onEvent(String eventName) throws RemoteException;
}
