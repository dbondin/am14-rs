package ru.applmath.am14im.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Каллбэк для серверных сообщений
 * 
 * @author dbondin
 */
public interface IMMessageReceiver extends Remote {
	/**
	 * Вызывается, когда мне приходит новое сообщение
	 * 
	 * @param contact от того сообщение
	 * @param message текст сообщения
	 * @throws RemoteException
	 */
	void messageReceived(IMContact contact, String message) throws RemoteException;
	
	/**
	 * Вызывается, когда изменился список моих контактов
	 * 
	 * @throws RemoteException
	 */
	void myContactsUpdated() throws RemoteException;
	
	/**
	 * Сервер время-от времени дергает этот метод, чтобы убедиться, что клиент все
	 * еще он-лайн.
	 * 
	 * @throws RemoteException
	 */
	void heartbeat() throws RemoteException;
}
