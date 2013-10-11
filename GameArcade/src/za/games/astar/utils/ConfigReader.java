/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  Utility class to read values in a given properties file 
'				so that we do not hard code values where possible 
'
' Notes: 
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

import za.games.astar.exceptions.ConfigException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigReader  {
	
	/*
	 * Read Properties file into HashMap
	 * @param propertyFileName
	 * 
	 * */
	
	public static HashMap readConfigProperties(String propertyFileName) throws ConfigException{
		HashMap hm = new HashMap();
		String value;
	
		//read the properties file
		Properties prop = new Properties();
		InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream( propertyFileName); 
		if(in != null){
			try {
				prop.load(in);
				Set ht = (Set) prop.keySet();
						
				Iterator e = ht.iterator(); 
		    	while(e.hasNext()){
		    		value = e.next().toString();
		    		hm.put(value,prop.get(value));
		    	}
			}catch(IOException ioe){
				throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propertyFileName + "' \n" + ioe.getMessage(), ioe);
			}catch(IllegalArgumentException ie){
				throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propertyFileName + "' \n" + ie.getMessage() , ie);
			}
		}else
			throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propertyFileName + "' could not be found." );
    	return hm;
	}
	
	
	/*
	 * 
	 * Reads porperty values from a give property file
	 * 
	 * @param propFileName
	 * @param prop
	 * 
	 * */
	public static String readConfigPropertyValue(String propFileName, String prop)throws ConfigException{
		String returnValue = "";
		//read the properties file
		Properties properties = new Properties();
		InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(propFileName); 
		
		if(in != null){
		
			try{
	    		properties.load(in);
	    		returnValue = properties.getProperty(prop);
			}catch(IOException ioe){
				throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propFileName + "' \n" + ioe.getMessage(), ioe);
			}catch(IllegalArgumentException ie){
				throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propFileName + "' \n" + ie.getMessage() , ie);
			}
		}else
			throw new ConfigException("readConfigPropertyValue:Error reading property file:' " + propFileName + "' could not be found." );
    	
		return returnValue;
		
	}

}
