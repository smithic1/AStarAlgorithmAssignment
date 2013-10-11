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
package za.games.astar.test;

import java.util.ArrayList;
import java.util.HashMap;

import za.games.astar.AStarPlayer;
import za.games.astar.utils.ConfigReader;
import za.games.astar.utils.FileValidator;
import junit.framework.TestCase;

public class FileValidatorTest extends TestCase {
	

	protected void setUp() throws Exception {
		super.setUp();
		
		/*
		//create CostHashMap
		terrainMovingCost.put("@","1");
		terrainMovingCost.put("X","1");
		terrainMovingCost.put("~","-1");
		terrainMovingCost.put(".","1");
		terrainMovingCost.put("*","2");
		terrainMovingCost.put("^","3");
		
		//create arrListInput (represent input file)
		arrListInput.add("@#^.");
		arrListInput.add(".*~~X");
		*/
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testValidChars() {
		
		FileValidator fv = new FileValidator();
		
		ArrayList arrListInput = new ArrayList();
		arrListInput.add("@#^.");
		arrListInput.add(".*~~X");
		assertTrue(fv.validateChars(arrListInput));
	}

	public void testHasStartChar() {
		FileValidator fv = new FileValidator();
		
		
		ArrayList arrListInput = new ArrayList();
		arrListInput.add("@#^.");
		arrListInput.add(".*~~X");
		
		assertTrue(fv.hasStartChar(arrListInput, "@"));
		
	
		
		
	}
	
	
	
	public void testHasNoStartChar() {
		FileValidator fv = new FileValidator();
		ArrayList arrListInput = new ArrayList();
		
		arrListInput.add(".*~~^");
		arrListInput.add(".*~~.");
		
		assertTrue(fv.hasStartChar(arrListInput, "@"));
		
	}
	
	public void testHasMoreThanOneStart(){
		FileValidator fv = new FileValidator();
		
		ArrayList arrListInput = new ArrayList();
		
		arrListInput.add("@@^.");
		assertTrue(fv.hasStartChar(arrListInput, "@"));
	}
	

	public void testHasGoalChar() {
		FileValidator fv = new FileValidator();
		
		ArrayList arrListInput = new ArrayList();
		arrListInput.add("@#^.");
		arrListInput.add(".*~~X");
		
		assertTrue(fv.hasStartChar(arrListInput, "X"));
		
		arrListInput.add("@#^.");
		assertTrue(fv.hasStartChar(arrListInput, "X"));
		
		assertTrue(fv.hasStartChar(arrListInput, "X"));
		
	}
	
	public void testHasNoGoalChar() {
		FileValidator fv = new FileValidator();
		
		ArrayList arrListInput = new ArrayList();
		arrListInput.add("@#^.");
		arrListInput.add(".*~~.");
		
		assertTrue(fv.hasStartChar(arrListInput, "X"));
		
	}
	
	public void testHasMoreThanOneGoalChar(){
		
			FileValidator fv = new FileValidator();
			
			ArrayList arrListInput = new ArrayList();
			arrListInput.add("X#^.");
			arrListInput.add(".*~~X");
			
			assertTrue(fv.hasStartChar(arrListInput, "X"));
			
	}
	

}
