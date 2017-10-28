package ru.applmath.dbondin.corbatictaktoeserver;

import java.io.PrintWriter;

public interface HttpProcessor {
	String getContentType();
	void printData(PrintWriter writer);
}
