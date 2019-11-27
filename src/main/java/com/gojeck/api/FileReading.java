package com.gojeck.api;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReading {
	private String filePath;

	public FileReading(String fileName) {
		this.filePath = APIHelper.getResourceFolderPath() + fileName;
	}

	public String[] readFile() throws Exception {
		String fileData = readFileAsString(filePath);
		String[] lines = fileData.split("\\r?\\n");
		return lines;

	}

	private String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
		return data;
	}
}
