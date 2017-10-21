package ru.applmath.rmicalc.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiCalcEntry extends Remote {
	RmiCalc login(String login, String password) throws RemoteException;
}
