package com.basicapiprogramming.kalai.genericfiles;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Settings {

	private static Properties properties = loadProperties();
	
	public static Properties getInstance() {
		return properties;
	}
	
	private static Properties loadProperties() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("./GlobalSettings.properties")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
}
