package com.newgen.log;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LogMe {
	
    public static final Logger logger = Logger.getLogger(LogMe.class);

	public static void intitalizeLog(){
    	try {
        	StringBuilder log4jPropertyFile = new StringBuilder(System.getProperty("user.dir"))
        	.append(System.getProperty("file.separator"))
        	.append("log4j.properties");
        	System.out.println("Path of log4j file: "+ log4jPropertyFile);
        	InputStream stream = new FileInputStream(log4jPropertyFile.toString());        	
        	Properties p = new Properties();
        	p.load(stream);
        	stream.close();
        	PropertyConfigurator.configure(p);
        	logger.info("Logger is configured successfully");
        	logger.info("Logger is configured successfully from : " + log4jPropertyFile);
		} catch (Throwable t) {
			System.out.println(t);
		}
	}

	
}
