package ru.axmexa.spring.jms.listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

public class ScriptRunnerFile implements ScriptRunner {

	@Override
	public JSONObject runScript(String fileName) {
		
		Runtime runTime = Runtime.getRuntime();
		String userDir = System.getProperty("user.dir").replaceAll("\\\\\\\\", "\\\\");
		String execString = 
				"perl "
				+ userDir 
				+ File.separator
				+ fileName
				+ ".pl";
		System.out.println("TRY TO RUN: " + execString);
		Process process = null;;
		try {
			process = runTime.exec(execString);
		} catch (IOException e) {
			if (null != process)
				process.destroy();
			e.printStackTrace();
		}

		InputStream inputStream = process.getInputStream();
		InputStream errorStream = process.getErrorStream();

		String std = MessageUtil.getRestrictString(inputStream);
		String err = MessageUtil.getRestrictString(errorStream);
		
		JSONObject answer = new JSONObject();
		answer.put("std", std);
		answer.put("err", err);
		
		return answer;
	}

}
