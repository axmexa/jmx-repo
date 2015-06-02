package ru.axmexa.spring.jms.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class MessageMaker {

	
	private static final String SCRIPT_NAME = "0";
	private static final String SCRIPT_TEXT = "1";
	private static final String STOP_MESSAGE = "2";
	private static final String UNDEFINED_TYPE_MESS  = "{\"std\":\"\",\"err\": \"message type is undefined\"}";
	
	public static JSONObject createAnswerMessage(String incomningText) throws Exception {

		JSONObject incomJSON = new JSONObject(incomningText);
		String messType = incomJSON.getString("type");
		String messValue = incomJSON.getString("value");
		
		final JSONObject answerJSON;
		if (SCRIPT_NAME.equals(messType)){
			answerJSON = new ScriptRunnerFile().runScript(messValue);
		}else if (SCRIPT_TEXT.equals(messType)){
			answerJSON = new ScriptRunnerText().runScript(messValue);
		}else if (STOP_MESSAGE.equals(messType)){
			throw new StopConsumerException();
		}else
			answerJSON = new JSONObject(UNDEFINED_TYPE_MESS);
		return answerJSON;

	}


}
