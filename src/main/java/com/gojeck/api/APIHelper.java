package com.gojeck.api;

import java.io.File;

public class APIHelper {
	
	public static String getResourceFolderPath() {
		return System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
				+ "resources" + File.separator + "gojeck" + File.separator;
	}

}
