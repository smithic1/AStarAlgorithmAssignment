/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  Prints the calculated terrain map to a file.
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

import java.awt.Point;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


import za.games.astar.AStarPlayer;
import za.games.astar.exceptions.ConfigException;
import za.games.astar.pathfinder.TerrainMap;
import za.games.astar.pathfinder.Node;


public class PrintTerrainMap {
	
	static final String BEST_PATH_MARKER_PROP = "BEST_PATH_MARKER";
	static final String OUPUT_FILE_NAME_PROP = "OUTPUT_FILENAME";
	
	public PrintTerrainMap(TerrainMap map, ArrayList shortestPath){
		LogUtil.debug(" ------------- print TerrainMap Result ------");
		boolean skip = false;
		//String outputFileName ;
		String bestPathChar;
		BufferedWriter outputFile = null;
		
		try {
			//outputFileName = ConfigReader.readConfigPropertyValue(AStarPlayer.PROPERTY_SET, OUPUT_FILE_NAME_PROP);
			bestPathChar = ConfigReader.readConfigPropertyValue(AStarPlayer.PROPERTY_SET, BEST_PATH_MARKER_PROP);
		} catch (ConfigException e1) {
			//set to default values.
			//outputFileName = "large_map.txt";
			bestPathChar   = "#";
		}
		
		
		
		try{
			 outputFile = new BufferedWriter(new FileWriter("out/large_map.txt"));  //somehow the compile did not like it with variable, so use literal 
			
			Node node;
			for(int y=0; y<map.getMapHeight(); y++) {
				 for(int x=0; x<map.getMapWidth(); x++) {
					 skip = false;
					 node = map.getNode(x, y);
					 for(int k=0; k<shortestPath.size(); k++){
						 Point p = (Point) shortestPath.get(k);
						 if(p.x == node.getXCoordinate() && p.y == node.getYCoordinate()){
							 outputFile.write(bestPathChar);
							 skip = true;
							 break;
						 }
						 
					 }
					 if(!skip){
						 outputFile.write(node.getSymbol());
					 }	 
				 }
				 outputFile.write("\n");
			}
		}catch(IOException ioe){
			
		}finally{
			try {
				outputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
