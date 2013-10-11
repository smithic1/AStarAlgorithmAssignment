
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
' Amendments:
'
' ChangeNo.		Name			Date				 Case ID/Description
' 001           	
''----------------------------------------------------------------------------------------------------------------------
*/
package za.games.astar.exceptions;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568526359463039567L;
	// default constructor
	public InvalidInputException() {
		super();
	}
	// constructor that takes the String detailed information we pass while
	// raising an exception
	public InvalidInputException(String str) {
		super(str);
	}
	// constructor that remembers the cause of the exception and
	// throws the new exception
	public InvalidInputException(Throwable originalException) {
		super(originalException);
	}
	
	// first argument takes detailed information string created while
	// raising an exception
	// and the second argument is to remember the cause of the exception
	public InvalidInputException(String str, Throwable originalException) {
		super(str, originalException);
	}
	
	public String getMessage()
    {
        return super.getMessage();
    }
}
