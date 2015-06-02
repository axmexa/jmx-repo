package ru.axmexa.spring.jms.listener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

public class ScriptRunnerText implements ScriptRunner {

	@Override
	public JSONObject runScript(String text) {
		
		File file = new File("temp.pl");
		Process process = null;;
		try {
			FileUtils.writeStringToFile(file, text);
		
			Runtime runTime = Runtime.getRuntime();
			String execString = 
					"perl "
					+ file.getAbsolutePath();
			System.out.println("TRY TO RUN: " + execString);
			process = runTime.exec(execString);
		} catch (IOException e1) {
			if (file.exists())
				FileUtils.deleteQuietly(file);
			if (null != process)
				process.destroy();
			e1.printStackTrace();
		}
		InputStream inputStream = process.getInputStream();
		InputStream errorStream = process.getErrorStream();

		String std = MessageUtil.getRestrictString(inputStream);
		String err = MessageUtil.getRestrictString(errorStream);
		
		JSONObject answer = new JSONObject();
		answer.put("std", std);
		answer.put("err", err);
		
		if (file.exists())
			FileUtils.deleteQuietly(file);
		if (null != process)
			process.destroy();
		
		return answer;
	}

}
