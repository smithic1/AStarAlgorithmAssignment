/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  The class that actually  plays the game.		 
'		 Prepare all the settings for the game and do validation on the file
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
package za.games.astar;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import za.games.astar.calculator.ManhattenDistanceCalculator;
import za.games.astar.exceptions.ConfigException;
import za.games.astar.exceptions.InvalidInputException;
import za.games.astar.impl.AStarGameImpl;
import za.games.astar.pathfinder.AStarAlgorithm;
import za.games.astar.pathfinder.TerrainMap;
import za.games.astar.utils.ConfigReader;
import za.games.astar.utils.FileValidator;
import za.games.astar.utils.LogUtil;
import za.games.astar.utils.PrintTerrainMap;


public class AStarPlayer implements AStarGameImpl{
	 
	private static AStarPlayer aStarPlayerObj;
	
	public static final String PROPERTY_SET = "terrainMap.properties";
	private static final String START_CHAR   = "START_CHAR";
	private static final String GOAL_CHAR    = "GOAL_CHAR";
	
	private static int mapWidth = 0;
	private static int mapHeight = 0;
	private static int startX = 0;
    private static int startY = 0;
    private static int goalX = 0;
    private static int goalY = 0;
    
    private BufferedReader terrainBufferReader = null;
	private char[][] terrainMap2D ;
	private ArrayList bufferArrList = new ArrayList();
	private TerrainMap terrainMap;
    public static HashMap terrainCharMap;
    public static HashMap terrainMovingCost;
    public static String startChar = null; 
    public static String goalChar = null;
    	
	LogUtil logger = new LogUtil();
	/**
	 * Private constructor so that it cannot be initialized 
	 */
	private AStarPlayer(){
		
	}
	
	/*
	 * Return instance of class - lazy initialization
	 * */
	public static AStarPlayer getInstance(){
		
		if(aStarPlayerObj == null){
			aStarPlayerObj = new AStarPlayer();
		}
		
		return aStarPlayerObj;
	}
	
	/*Init must setup all parameters
	 * Determine: start & goal char in map
	 * Read different chars in map & their cost
	 * 
	 * */
	public void init() throws  ConfigException{
		HashMap hmConfig;
		hmConfig = ConfigReader.readConfigProperties(PROPERTY_SET);
		
		createTerrainCharMap(hmConfig);
		createMoveCostMap(hmConfig);
		setStartChar(hmConfig);
		setGoalChar(hmConfig);
		
		if(startChar == null || "".equals(startChar)) {
			throw new InvalidInputException("No start char in property file.");
		}
		
		if(goalChar == null || "".equals(goalChar)) {
			throw new InvalidInputException("No goal char in property file.");
		}
		
	}
	
	/*
	 * start to start the game
	 * */
	public void start() throws IOException, InvalidInputException{
		
		doPreGameValidation();
		
		calculateHeight();
		calculateWidth();
		
		if(mapWidth == 0 && mapHeight == 0){
			throw new InvalidInputException("Empty map: Could not find height or width");
		}
		
		createTerrainMap(mapWidth, mapHeight);
		
		ManhattenDistanceCalculator heuristicDist = new ManhattenDistanceCalculator();
		
		getAStarAlg(terrainMap, heuristicDist);
		
		LogUtil.debug("********** DONE PLAYING, THANK YOU *********");
		System.out.println("********** DONE PLAYING, THANK YOU *********");
		
	}
	
	
	
	/*
	 * The validation that must be done before the game can start
	 * 1. Should be able to read Input file 
	 * 2. All the characters in the file should be valid
	 * 3. There should be a start character (1 and only 1)
	 * 4. There should be a goal character (1 and only 1)
	 * */
	public void doPreGameValidation() throws InvalidInputException, IOException{
		readFileBuffer();
		
		FileValidator fv = new FileValidator();
		if(!fv.validateChars(this.bufferArrList)){
			throw new InvalidInputException("Invalid Input: Invalid characters in file."  );
		}
		
		if(!fv.hasStartChar(this.bufferArrList, this.startChar)){
			throw new InvalidInputException("Invalid Input: Could not determine correct Start node."  );
		}
		
		if(!fv.hasGoalChar(this.bufferArrList, this.goalChar)){
			throw new InvalidInputException("Invalid Input: Could not determine correct Goal node."  );
		}
	}

	/*
	 * Iterate through HashMap to find all elements of terrain map
	 * Elements that ends with _CHAR
	 * Should look this
	 * Key START_CHAR, Value @
	 * Key GOAL_CHAR,  Value X 
	 * ...
	 * ...
	 * 
	 * */
	private void createTerrainCharMap(HashMap hm) {
		terrainCharMap = new HashMap();
		LogUtil.debug("------- createTerrainCharMap -----------");
		
		for (Iterator it = hm.entrySet().iterator(); it.hasNext();) {
			Map.Entry pairs = (Map.Entry) it.next();
			LogUtil.debug(pairs.getKey() + " = " + pairs.getValue());
			if(pairs.getKey().toString().indexOf("_CHAR") >=0){
				terrainCharMap.put(pairs.getKey(),pairs.getValue());
			}
		}
		LogUtil.debug("-------------------------------");
	}
	
	/*
	 * Iterate through HashMap to find cost for all elements of terrain map
	 * Elements that ends with _COST
	 * Should look this
	 * Key @, Value 1
	 * Key ^, Value 3 
	 * ...
	 * ...
	 * 
	 * */
	private void createMoveCostMap(HashMap hm){
		terrainMovingCost = new HashMap();
		String costChar;
		
		LogUtil.debug(" ---------- createMoveCostMap ----------");
		for (Iterator it = hm.entrySet().iterator(); it.hasNext();) {
			Map.Entry pairs = (Map.Entry) it.next();
			
			if(pairs.getKey().toString().indexOf("_COST") >=0){
				costChar = pairs.getKey().toString().replace("_COST", "_CHAR");
				terrainMovingCost.put(hm.get(costChar),pairs.getValue());
				LogUtil.debug(pairs.getKey() + " = " + pairs.getValue());
			}
		} 
		LogUtil.debug("-------------------------------");
	}
	
	/*
	 * Determine the start char
	 * if no start char can be found here, it mean the terrainMap properties is not correct
	 * */
	private void setStartChar(HashMap hm)  {
		for (Iterator it = hm.entrySet().iterator(); it.hasNext();) {
			Map.Entry pairs = (Map.Entry) it.next();
			
			if(START_CHAR.equalsIgnoreCase(pairs.getKey().toString())){
				startChar = (String) pairs.getValue();
				break;
			}
		} 
	}
	private void setGoalChar(HashMap hm){
		for (Iterator it = hm.entrySet().iterator(); it.hasNext();) {
			Map.Entry pairs = (Map.Entry) it.next();
			
			if(GOAL_CHAR.equalsIgnoreCase(pairs.getKey().toString())){
				goalChar = (String) pairs.getValue();
				break;
			}
		} 
	}
	
	/*
	 * Read terrainBufferReader into ArrayList.
	 * */
	private void readFileBuffer() throws IOException{
		
		String sCurrentLine;
		
		while((sCurrentLine = terrainBufferReader.readLine()) != null && !"".equals(sCurrentLine)){
			bufferArrList.add( sCurrentLine);   
		}
	}
	
	/*
	 * Do call to get the actual A* Algorithm
	 * Return the shortest path from Start to Goal, and print it in a output file.
	 * */
	private void getAStarAlg(TerrainMap terrainMap,ManhattenDistanceCalculator heuristicDist ){

		AStarAlgorithm aStarAlg = new AStarAlgorithm(terrainMap, heuristicDist);
		try{
			ArrayList shortestPath = aStarAlg.calculateShortestPath(startX, startY, goalX, goalY);
			new PrintTerrainMap(terrainMap, shortestPath);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Use bufferArrList (input) to firstly create a 2 dimensional array 
	 * which will serve as input to the TerrainMap class to create the nodes for game
	 * 
	 * */
	public void createTerrainMap(int mapWidth, int mapHeight) {
		String sCurrentLine;
		int x = 0;
	
		//create 2d terrainMap
		terrainMap2D = new char[mapWidth][mapHeight];
		Iterator itr =  bufferArrList.iterator();
		
		try{
		    while(itr.hasNext()){
		    	sCurrentLine = itr.next().toString();
				char[] arrTmp = sCurrentLine.toCharArray();   //example: @,*,^,^,^
				terrainMap2D[x] = arrTmp; 
				x++;
			}
			
		    LogUtil.debug("************ creatig TerrainMap object *********");
		    LogUtil.debug("map Width = " + mapWidth + ": map Height" + mapHeight);
		    
		    terrainMap = new TerrainMap(mapWidth, mapHeight, terrainMap2D);
				
			startX = terrainMap.getStartNodeX();
			startY = terrainMap.getStartNodeY(); 
			goalX  = terrainMap.getGoalNodeX();
			goalY  = terrainMap.getGoalNodeY();
			
			LogUtil.debug("Start node = " + startChar +  ",x:" + startX + ",y:"  + startY + " , Goal node = " + goalChar + ",x: " + goalX + ",y" + goalY);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Reads the bufferArrList to determine the height of the map
	 * and put it in instance variable mapHeight.
	 * */
	public void calculateHeight(){
		int count = 0;
		
		Iterator itr = bufferArrList.iterator();
		
	    while(itr.hasNext()){
	    	itr.next();
	    	count++;
	    }

	    this.mapHeight = count;
			
	}
	
	/*
	 * Reads the bufferArrList to determine the width of the map
	 * and put it in instance variable mapWidth.
	 * */
	public void calculateWidth(){
		int width = 0;
		String sCurrentLine ;
		
		try {
			Iterator itr = bufferArrList.iterator();
			while(itr.hasNext()){
				sCurrentLine = itr.next().toString();
				width = sCurrentLine.toCharArray().length;
				break;
			}
						
			this.mapWidth = width;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	/*
	 * Override clone. Clone not supported for Singleton
	 * */
	public Object clone() throws CloneNotSupportedException{
		throw new CloneNotSupportedException();
	}
	
	
	public void setBufferedReaderIn(BufferedReader mapFile){
		terrainBufferReader = mapFile;
	}
	
}
