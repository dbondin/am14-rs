package ru.applmath.am14im.api;

import java.rmi.RemoteException;

/**
 * Точка входа в IM
 * 
 * @author dbondin
 *
 */
public interface IMEntry {
	
	/**
	 * Приглашение от сервера
	 * 
	 * @return
	 * @throws RemoteException
	 */
	String greeting() throws RemoteException;
	
	/**
	 * 
	 * Логинимся на сервер.
	 * 
	 * @param login логин
	 * @param password пароль
	 * @param messageReceiver обработчик входящих сообщений
	 * @return рабочий экземпляр {@link IM}
	 * @throws RemoteException
	 */
	IM login(String login, String password, IMMessageReceiver messageReceiver) throws RemoteException;
	
	/**
	 * 
	 * Регистрация нового пользователя
	 * 
	 * @param login логин
	 * @param password пароль
	 * @throws RemoteException
	 */
	void register(String login, String password) throws RemoteException;
}
