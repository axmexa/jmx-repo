package ru.axmexa.spring.jms.listener;

import org.json.JSONObject;

public interface ScriptRunner {

	public JSONObject runScript(String text);
}
