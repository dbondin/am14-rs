package ru.applmath.am14im.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * API непосредственно инстант-мессаджера
 * 
 * @author dbondin
 *
 */
public interface IM extends Remote {
	/**
	 * Поиск контактов по части логина. Например поис по loginParretn="dim"
	 * найдет пользователей "dima", "dimka", "Dimon" и т.д.
	 * Не зависит от регистра.
	 * 
	 * @param loginPattern часть логина для поиска
	 * @return список контаков, удовлетворяющий условиям поиска
	 * @throws RemoteException
	 */
	List<IMContact> findContacts(String loginPattern) throws RemoteException;
	
	/**
	 * Возвращает список контактов в моем контакт-листе.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List<IMContact> getMyContacts() throws RemoteException;
	
	/**
	 * Добавляет контакт в мой контакт-лист
	 * 
	 * @param contact
	 * @throws RemoteException
	 */
	void addMyContact(IMContact contact) throws RemoteException;
	
	/**
	 * Удаляет контакт из моего контакт-листа
	 * 
	 * @param contact
	 * @throws RemoteException
	 */
	void removeMyContact(IMContact contact) throws RemoteException;
	
	/**
	 * Выйти из системы
	 * 
	 * @throws RemoteException
	 */
	void logoff() throws RemoteException;
}
