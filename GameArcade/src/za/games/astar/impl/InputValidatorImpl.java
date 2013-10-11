/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
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
package za.games.astar.impl;

import java.util.ArrayList;

public interface InputValidatorImpl {
	boolean validateChars(ArrayList arrList);
	boolean hasStartChar(ArrayList arrList, String startChar);
	boolean hasGoalChar(ArrayList arrList, String goalChar);
}
