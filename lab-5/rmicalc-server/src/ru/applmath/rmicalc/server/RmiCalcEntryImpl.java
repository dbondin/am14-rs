package ru.applmath.rmicalc.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ru.applmath.rmicalc.api.RmiCalc;
import ru.applmath.rmicalc.api.RmiCalcEntry;

public class RmiCalcEntryImpl extends UnicastRemoteObject implements RmiCalcEntry {
	
	private static final long serialVersionUID = -815005242695234404L;

	public RmiCalcEntryImpl() throws RemoteException {
	}

	@Override
	public RmiCalc login(String login, String password) throws RemoteException {
		if("dbondin".equals(login) && "qwerty".equals(password)) {
			return new RmiCalcImpl();
		}
		throw new RemoteException("Login failed");
	}
}
