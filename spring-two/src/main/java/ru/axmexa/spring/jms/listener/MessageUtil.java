package ru.axmexa.spring.jms.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageUtil {
	
	private final static int maxStringBytesSize = 1024 * 1024;

	public static String getRestrictString(InputStream is) {

		StringBuilder sb = new StringBuilder();

		int counter = maxStringBytesSize - 1;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try  {

			int value = 0;
			while ((value = br.read()) != -1 && counter > 0) {
				sb.append((char) value);
				counter--;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();

	}
}
