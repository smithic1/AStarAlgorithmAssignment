/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description: Class based on the strategy design principle where algorithms are uses.
'			   Games that use algorithms, will always init, calculate a field of play (terrainMap)	 
'              do preGame validations and start the game.
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

import java.io.IOException;

import za.games.astar.exceptions.ConfigException;
import za.games.astar.exceptions.InvalidInputException;

public interface AStarGameImpl {
	void init() throws ConfigException;
	void createTerrainMap(int mapWidth, int mapHeight);
	void doPreGameValidation() throws InvalidInputException, IOException;
	void start() throws IOException, InvalidInputException;
}
