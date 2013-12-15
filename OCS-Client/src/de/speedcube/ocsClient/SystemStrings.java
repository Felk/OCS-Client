package de.speedcube.ocsClient;

import java.io.IOException;
import java.util.Properties;

public class SystemStrings {
	static private Properties propertyFile;

	static {
		propertyFile = new Properties();
		try {
			propertyFile.load(SystemStrings.class.getResourceAsStream("/systemStrings.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String id) {
		String retString = propertyFile.getProperty(id);
		if (retString != null) {
			return retString;
		} else {
			return id;
		}
	}
}
