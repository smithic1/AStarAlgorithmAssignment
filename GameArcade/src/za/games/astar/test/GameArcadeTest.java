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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import za.games.astar.GameArcade;
import za.games.astar.exceptions.ConfigException;
import za.games.astar.exceptions.InvalidInputException;
import za.games.astar.utils.LogUtil;
import junit.framework.TestCase;

public class GameArcadeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*Make sure the is jus one input argument*/
	public void testMain() {
		String[] args = {"c:\\astarAlg\\intput.txt"};
		assertTrue(args.length == 1);

	}
	
	/* test input file file that is not int tht location*/
	public void testInputFileFileNotFound(){
		//Invalid file name
		
			String[] args = {"c:\\astarAlg\\intput.txt" };
			GameArcade ga = new GameArcade(args[0]);
			
	}
	
	public void testInputFileValid(){
		//Valid file
		GameArcade ga2 = new GameArcade("C:\\JUNIT_WORKSPACE\\GameArcade\\input files\\input.txt");
	}
	
	public void testPlayGame() throws InvalidInputException, ConfigException {
		
		
		try {
			GameArcade ga3 = new GameArcade("c:\\astarAlg\\intput.txt");
			BufferedReader terrainBufferReader = new BufferedReader(new FileReader("c:\\astarAlg\\intput.txt"));
			ga3.playGame();
		} catch (FileNotFoundException e) {
			assertNotNull(e.getMessage());
			
		}catch (InvalidInputException i){
			assertNotNull(i.getMessage());
		}catch (ConfigException ce){
			assertNotNull(ce.getMessage());
		}
		
	
	}
	
	

}
