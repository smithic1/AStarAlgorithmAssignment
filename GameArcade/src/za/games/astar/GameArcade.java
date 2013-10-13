/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  Main class of this application
'        		Create single instance of the A StarPlayer class that actually 
'        		plays the game.		 
'        		Takes in file name and location of the terrainMap for the game.
' 
'
' Notes: 
'
' Amendments:
'
' ChangeNo.		Name			Date				 Case ID/Description
' 001           	
''----------------------------------------------------------------------------------------------------------------------
*/



package za.games.astar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import za.games.astar.exceptions.ConfigException;
import za.games.astar.exceptions.InvalidInputException;
import za.games.astar.utils.LogUtil;

public class GameArcade {
	
	private BufferedReader terrainBufferReader = null;
	
	
	public GameArcade(String fileName){
		try{
			readInputFile(fileName);
		}catch(FileNotFoundException fnf){
			LogUtil.debug("Invalid input: Could not find file.");
			fnf.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public void playGame() throws InvalidInputException, ConfigException{
		
		//get instance of AStarPlayer, and let the games begin
		AStarPlayer aStarPlayer = AStarPlayer.getInstance();
		
		//set input file with terrain map
		aStarPlayer.setBufferedReaderIn(terrainBufferReader);
		
		//initialize variables for game
		try {
			aStarPlayer.init();
			aStarPlayer.start();
		} catch (ConfigException e1) {
			e1.printStackTrace();
		}catch (InvalidInputException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void clean() throws IOException{
		try{
			//cleaning code
		}
		finally{
			try {
				if (terrainBufferReader != null)
					terrainBufferReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			
		}
	}
	private void readInputFile(String fileName) throws IOException{
		try{
			terrainBufferReader = new BufferedReader(new FileReader(fileName));
		}catch(FileNotFoundException fnf){
			fnf.printStackTrace();
		}
		
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IllegalArgumentException  {
		 
		if(args.length == 1){
				System.out.println("Reading " + args[0]);

				try{
					GameArcade ga = new GameArcade(args[0]);
					ga.playGame();
					ga.clean();
				}catch(Exception e){
					System.out.print("Invalid input: Could not find file.");
				}
		}else{
				throw new IllegalArgumentException("Invalid input parameter: Please valdiate your input.");
				
		}
	}
}
