package com.gojeck.api;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataReader {
	public static synchronized Map<String, String> readData(String fileName, String elementName) throws Exception {
		String filePath = null;
		filePath = APIHelper.getResourceFolderPath() + fileName;
		JsonElement root = new JsonParser().parse(new FileReader(filePath));
		JsonObject jsonObject = root.getAsJsonObject();
		JsonElement some = jsonObject.get(elementName);
		JsonObject testData = some.getAsJsonObject();
		Map<String, String> testDataMap = new HashMap<String, String>();
		for (Map.Entry<String, JsonElement> entry : testData.entrySet()) {
			testDataMap.put(((String) entry.getKey()).toString(), ((JsonElement) entry.getValue()).getAsString());
		}
		return testDataMap;
	}
}
