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
package za.games.astar.calculator;

import java.awt.Point;

public class ManhattenDistanceCalculator implements DistanceCalculatorImpl {

	public int getEstDistanceToGoal(Point start, Point goal) {
		int manhattenDistance =  (Math.abs(start.x-goal.x) + Math.abs(start.y-goal.y));
		return manhattenDistance;
	}
	 
}
