/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  Entity class that holds the information for the nodes in the path
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
package za.games.astar.pathfinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class Node {
	
	private int yCoordinate;
	private int xCoordinate;
	
	//Default to false. Will be set to true if needed when building TerrainMap
	private boolean isRestricted = false; 	
	private boolean isStart 	 = false;
	private boolean isGoal       = false;
	
	
	private char	symbol;
	private int 	moveCost;
	private int     fScore = 0;
	private int 	nodeId ;
	
	protected TerrainMap terrMap;
	
	protected int distanceFromStart;
	protected int manhattenDistanceToGoal;  

    Node parentNode;
	
	/* **
	 * Constructor
	 * @param x
	 * @param y
	 * @param tm
	 * */
	 public Node(int x, int y, TerrainMap tm){
		 
		 this.xCoordinate = x;
		 this.yCoordinate = y;
		 this.terrMap 	  = tm;
	 }
	 
	 /*
	 * Constructor
	 * 
	 * */
	 Node (int x, int y, TerrainMap tm, char sIn, int distFromStart, boolean restricted, boolean isStartIn, boolean isGoalIn) {
		 
		 this.xCoordinate 	= x;
		 this.yCoordinate 	= y;
		 this.terrMap 		= tm;
		 
		 this.distanceFromStart = distFromStart;
		 this.isRestricted   	= restricted;
		 this.isStart 			= isStartIn;
		 this.isGoal 			= isGoalIn;
		 this.symbol 			= sIn;
	 }
	 
	 /* *
	  * Get adjacent nodes
	  * In theory there are 8 possible combinations 
	  *		 BUT
	  *		 cannot move horizontal if x reached terrMap.mapWidth or (if x=0, cannot -1)
	  *		 cannot move vertical if y reached terrMap.mapHeight  or (if y=0, cannot -1)
	  *			
	  *		 only add if walkable\not restricted notes
	  * */
	 public ArrayList getAdjacentNodeList() {
		 ArrayList adjNodeList = new ArrayList();
		 Node currentNode;
			 
		 int xBorder = terrMap.getMapWidth() -1;
		 int yBorder = terrMap.getMapHeight() -1;
		
		  
		//row below diagonal right
		 if(this.xCoordinate < xBorder && this.yCoordinate < yBorder){
			 currentNode = terrMap.getNode(this.xCoordinate+1, this.yCoordinate+1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }
		 
		 //horizontal in currant row - move to the right
		 if(this.xCoordinate < xBorder){
			 currentNode = terrMap.getNode(this.xCoordinate+1, this.yCoordinate); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }	 
		 
		//row above diagonal right
		 if(this.xCoordinate < xBorder && this.yCoordinate > 0){
			 currentNode = terrMap.getNode(this.xCoordinate+1, this.yCoordinate-1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }
		
		 //go to row above
		 if(this.yCoordinate > 0){
			 currentNode = terrMap.getNode(this.xCoordinate, this.yCoordinate-1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }
		 
		 
		//go to row below
		 if(this.yCoordinate < yBorder){
			 currentNode = terrMap.getNode(this.xCoordinate, this.yCoordinate+1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }	 
		 
		
	 	
		 //row below diagonal left
		 if(this.xCoordinate > 0 && this.yCoordinate < yBorder){
			 currentNode = terrMap.getNode(this.xCoordinate-1, this.yCoordinate+1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }
		
		 //horizontal in currant row - move to the left
		 if(this.xCoordinate > 0){
			 currentNode = terrMap.getNode(this.xCoordinate-1, this.yCoordinate); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 }

		 //row above diagonal left
		 if(this.xCoordinate> 0 && this.yCoordinate > 0){
			 currentNode = terrMap.getNode(this.xCoordinate-1, this.yCoordinate-1); 
			 if(!currentNode.getIsRestricted())
				 adjNodeList.add(currentNode);
		 } 
		 
			 
		 
		 return adjNodeList;
	 }
	 
	 /*
	  * Override public boolean equals(Object obj)
	  * 
	  */ 
	 
	   public boolean equals(Object obj) {
		
		   if(obj == null)
			   return false;
		   if(obj instanceof Node){
				 Node node = (Node)obj;
				 return (node.getXCoordinate() == xCoordinate) && (node.getYCoordinate() == yCoordinate);
		   }else
			   return true;

	   }
	   
	   /*
		  * Override public String toString()
		  * Return String presentation of Node
		  * 
		  */ 

	
	  public String toString() {
		String strToReturn = "";
		strToReturn = "x = " + this.xCoordinate + ", y = " + this.yCoordinate + ":" + "Symbol = " + this.symbol + ":fScore = " + this.fScore +"distanceFromStart = " + this.distanceFromStart ; 
		/*+ ", moveCost = " + this.moveCost + "\n" +
					 "distanceFromStart = " + this.distanceFromStart  + "," +   "manhattenDistanceToGoal = "+ this.manhattenDistanceToGoal 
				      "isStart =  " + this.isStart + ", isGoal = " + this.isGoal;*/ 
		
		
		  return strToReturn;
	  }
	 
	 //Getters & Setter
	 public void setIsRestricted(boolean restricted){
		 this.isRestricted = restricted;
	 }
	 
	 public boolean getIsRestricted(){
		 return this.isRestricted;
	 }
	 
	 public void setIsStart(boolean blnIsStart){
		 this.isStart = blnIsStart;
	 }
	 
	 public boolean getIsStart(){
		 return this.isStart;
	 }
	 
	 public void setIsGoal(boolean blnIsGoal){
		 this.isGoal = blnIsGoal;
	 }
	 
	 public boolean getIsGoal(){
		 return this.isGoal;
	 }
	 
	 public void setXCoordinate(int x){
		 this.xCoordinate = x;
	 }
	 
	 public int getXCoordinate(){
		 return this.xCoordinate;
	 }
	 
	 public void setYCoordinate(int y){
		 this.yCoordinate = y;
	 }
	 
	 public int getYCoordinate(){
		 return this.yCoordinate;
	 }
	 
	 public char getSymbol(){
		 return this.symbol;
	 }
	 
	 public void setSymbol(char sIn){
		 this.symbol = sIn;
	 }
	 
	 public int getDistanceFromStart() {
         return distanceFromStart;
	 }

	 public void setDistanceFromStart(int dist) {
         this.distanceFromStart = dist;
	 }
	 
	 public Node getParentNode() {
         return parentNode;
	 }
	
	 public void setParentNode(Node previousNode) {
	         this.parentNode = previousNode;
	 }
	 
	 public void setManhattenDistanceToGoal(int dist){
		 this.manhattenDistanceToGoal = dist;
	 }
	 
	 public int getManhattenDistanceToGoal(){
		 return this.manhattenDistanceToGoal;
	 }
	 
	 public void setMoveCost(int cost){
		 this.moveCost = cost;
	 }
	 
	 public int getMoveCost(){
		 return this.moveCost;
	 }
	 
     public Point getPoint() {
    	 return new Point(xCoordinate, yCoordinate);
     }
     
     public void setFScore(int fs){
    	 this.fScore = fs;
     }
     
     public int getFScore(){
    	 return this.fScore;
     }
   
     
     public int getNodeId(){
    	 return this.nodeId;
     }
     
     public void setNodeId(int id){
    	 this.nodeId = id;
     }
	 	
}
