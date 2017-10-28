package ru.applmath.dbondin.corbatictactoeserver;

import java.io.PrintWriter;

public interface HttpProcessor {
	String getContentType();
	void printData(PrintWriter writer);
}
