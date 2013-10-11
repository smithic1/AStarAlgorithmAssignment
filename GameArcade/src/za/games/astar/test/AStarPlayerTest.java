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

import java.util.HashMap;

import za.games.astar.exceptions.ConfigException;
import za.games.astar.utils.ConfigReader;
import junit.framework.TestCase;

public class AStarPlayerTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void readValidConfigFile() throws ConfigException{
		HashMap hmConfig;
		hmConfig = ConfigReader.readConfigProperties("terrainMap.properties");
		
		assertNotNull(hmConfig);

	}
	
	public void readInValidConfigFile() throws ConfigException{
		HashMap hmConfig;
		hmConfig = ConfigReader.readConfigProperties("terrain.properties");
		
		assertNotNull(hmConfig);

	}

	
	public void testInit() {
		try{
		readValidConfigFile();
		}catch(ConfigException e){
			assertNotNull(e.getMessage());
		}
		
		try{
			readInValidConfigFile();
			}catch(ConfigException e){
				assertNotNull(e.getMessage());
		}
		
	}
	/* TODO implement as I go...
	public void testStart() {
		
		fail("Not yet implemented"); // TODO
	}

	public void testCreateTerrainMap() {
		fail("Not yet implemented"); // TODO
	}

	public void testCalculateHeight() {
		fail("Not yet implemented"); // TODO
	}

	public void testCalculateWidth() {
		fail("Not yet implemented"); // TODO
	}

	public void testSetBufferedReaderIn() {
		fail("Not yet implemented"); // TODO
	}
	*/

}
