/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:Log utility that use Log4j to log messages and if applicable, errors.  
' 
'
' Notes: Logger:
'		 Log4j - make sure the log4j.properties file is in <project root folder>/resources
'
'		 
'
'
' Amendments:
'
' ChangeNo.		Name			Date				 Case ID/Description
' 001           	
''----------------------------------------------------------------------------------------------------------------------
*/
package za.games.astar.utils;

import org.apache.log4j.Logger;

import za.games.astar.AStarPlayer;
import za.games.astar.exceptions.ConfigException;

public class LogUtil {
	static  Logger logger = Logger.getLogger("log4j.category.org.example.bar");
	//static final String LOG_PROP_FILE = "log4j.properties";
	static final String DEBUG = "DEBUGGER_ON";
	static boolean debug = false;
	
	public LogUtil(){
		try {
			if("TRUE".equalsIgnoreCase( ConfigReader.readConfigPropertyValue(AStarPlayer.PROPERTY_SET,DEBUG)))
					debug = true;
		} catch (ConfigException e) {
			//just ignore and leave debug = false (default)
		}
	}
	public static void debug(String s){
		if(debug)
			logger.debug(s);
	}
}
