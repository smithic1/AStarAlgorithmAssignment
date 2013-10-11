/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 10 October 2013
' Description:  
' 
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
package za.games.astar.exceptions;

public class ConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public String getMessage()
    {
        return super.getMessage();
    }

}
