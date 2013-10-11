/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description: Utility class to do validation on the input file for this game 
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
package za.games.astar.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import za.games.astar.AStarPlayer;
import za.games.astar.exceptions.InvalidInputException;
import za.games.astar.impl.InputValidatorImpl;

public class FileValidator implements InputValidatorImpl{

	/*
	 * Check for valid Chars as created in AStarPlayer.createMoveCostMap() method
	 * terrainMovingCost
	 * 
	 * loop through ArrayList to validate chars
	 * Return false if char in arrList does not match the key in the terranMovingCost hashMap
	 * Return true  if all chars in the arrList is in the terranMovingCost hashMap
	 * 
	 * @param arrList
	 * */
	public boolean validateChars(ArrayList arrList)  {
		String sLine;
		
		//loop through arrList
		for(int i = 0; i < arrList.size(); i++){
			sLine = (String) arrList.get(i);
			char[] charInLine = sLine.toCharArray();
			
			for(int k=0; k<charInLine.length; k++){
				if(!AStarPlayer.terrainMovingCost.containsKey(String.valueOf(charInLine[k]))){
					return false;
				}
			}
		}
		
		//if you get here, all characters were valid
		return true;
	}
	
	/*
	 * Loop through ArrayList to find a StartChar. 
	 * 
	 * Return true there is one, and only one
	 * */
	public boolean hasStartChar(ArrayList arrList, String startChar) {
		String sLine;
		int count = 0;
		int ii = 0;
		
		//loop through array
		for(int i = 0; i < arrList.size(); i++){
			sLine = (String) arrList.get(i);    	//@*~.@^
			//count the startChar 
			ii =  sLine.length() - sLine.replaceAll(startChar, "").length();
			count += ii;
		}

		return (count == 1);
	}
	
	/*
	 * Loop through ArrayList to find a GoalChar. 
	 * 
	 * Return true there is one, and only one
	 * */

	public boolean hasGoalChar(ArrayList arrList, String goalChar) {
		String sLine;
		int count = 0;
		int ii = 0;
		
		//loop through array
		for(int i = 0; i < arrList.size(); i++){
			sLine = (String) arrList.get(i);    	//@*~.@^
			//count the startChar 
			ii =  sLine.length() - sLine.replaceAll(goalChar, "").length();
			count += ii;
		}

		return (count == 1);

	}

}
