package ru.applmath.dbondin.corbatictaktoeserver;

import org.omg.CORBA.StringHolder;

import ru.applmath.dbondin.idl.ticktacktoe.CellValueEnumHolder;
import ru.applmath.dbondin.idl.ticktacktoe.FieldHolder;
import ru.applmath.dbondin.idl.ticktacktoe.GamePOA;

public class GameImpl extends GamePOA {

	public boolean entry(String nickname, StringHolder gameCookie) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void exit(String gameCookie) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean getField(String gameCookie, FieldHolder outField) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean getMySign(String gameCookie, CellValueEnumHolder mySign) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getVersion() {
		//TODO: Make good version here
		return "corba tictaktoe server // dbondin";
	}
	
	public boolean makeMove(String gameCookie, int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
}
