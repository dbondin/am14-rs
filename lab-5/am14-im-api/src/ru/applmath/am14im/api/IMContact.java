package ru.applmath.am14im.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Контакт в {@link IM}
 * 
 * @author dbondin
 */
public interface IMContact extends Remote {
	/**
	 * Логин
	 * 
	 * @return
	 * @throws RemoteException
	 */
	String getLogin() throws RemoteException;
	
	/**
	 * Он-лайн статус
	 * 
	 * @return true если контакт онлайн
	 * @throws RemoteException
	 */
	boolean isOnline() throws RemoteException;
	
	/**
	 * Послать сообщение
	 * 
	 * @param message
	 * @throws RemoteException
	 */
	void sendMessage(String message)  throws RemoteException;
}
