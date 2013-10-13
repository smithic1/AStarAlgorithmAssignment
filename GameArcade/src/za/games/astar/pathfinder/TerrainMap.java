/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description:  holds information about the With, Height,Start position, Goal position and Restrictions on the map.
'               A place on the map is referred to by it's (x,y) coordinates,
' 				where (0,0) is the upper left corner, and x is horizontal and y is vertical.
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

package za.games.astar.pathfinder;

import java.awt.Point;
import java.util.ArrayList;

import za.games.astar.AStarPlayer;
import za.games.astar.utils.LogUtil;

public class TerrainMap {
	private int mapWidth;
	private int mapHeight;
	private int startNodeX;
	private int goalNodeX;
	private int startNodeY;
	private int goalNodeY;
	
	private ArrayList nodeMapArrList ;  		//map that consist of nodes
	private char[][] terrainMap = {{0}};
	
	/* *
	 * Constructor
	 *	
	 * @param with
     * @param height
	 * */
	public TerrainMap(int width, int height){
		mapWidth = width;
		mapHeight = height;
		
		createMap();
		
	}
	
	/* *
	 * Constructor
	 *	
	 * @param with
     * @param height
     * @param terrain
	 * */
	public TerrainMap(int width, int height, char[][] terrain){
		mapWidth = width;
		mapHeight = height;
		terrainMap = terrain;
		
		createMap();
		
	}
	
	/* *
	 * 
	 * Create an arrayList of nodes using the mapWidth & mapHeight
	 * 
	 * */
	private void createMap(){
		LogUtil.debug("*** TerrainMap: createMap() ****");
		
		Node node;
		char cSymbol;
		int idCount = 0;
		nodeMapArrList = new ArrayList();
		
		for(int x = 0; x < mapWidth;x++){
			
			nodeMapArrList.add(new ArrayList());
			
			for(int y= 0; y < mapHeight; y++){
				node = new Node(x,y, this);
								
				try {
					cSymbol = terrainMap[y][x];
					node.setSymbol(cSymbol);
					node.setMoveCost(getMoveCost(cSymbol));
					
					//set unique id for node
					idCount = idCount +1;
					node.setNodeId(idCount);
					
					//set restricted. Assumption: Not movable if cost = -1
					if(getMoveCost(cSymbol) <0){
	                       node.setIsRestricted(true);
					}      
	                
	                //set is Start
					if(AStarPlayer.startChar.equals(String.valueOf(cSymbol))){
							node.setIsStart(true);
							this.startNodeX = x;
							this.startNodeY = y;
					}
					
	                //set is Goal
					if(AStarPlayer.goalChar.equals(String.valueOf(cSymbol))){
						node.setIsGoal(true);
						this.goalNodeX = x;
						this.goalNodeY = y;
					}	
					
	                
		        } catch (Exception e) {
		        	e.printStackTrace();
		        }

		        ((ArrayList) nodeMapArrList.get(x)).add(node);
			}
			
		
		}
		
	}
	/*
	 * Use current char you want to add to terrain map
	 * locate in hashMap with all the terrainMovingCost
	 * The value that correlate to the key, is the cost of the move
	 * */
	private int getMoveCost(char c){
		return Integer.parseInt( (String) AStarPlayer.terrainMovingCost.get(String.valueOf(c)));
	}
	
		
	
	//Getters & Setters
	public int getMapWidth(){
		return this.mapWidth;
	}
	
	public int getMapHeight(){
		return this.mapHeight;
	}
	
	public int getStartNodeX(){
		return this.startNodeX; 
	}
	
	public int getGoalNodeX(){
		return this.goalNodeX;
	}
	
	public int getStartNodeY(){
		return this.startNodeY;
	}

	public int getGoalNodeY(){
		return this.goalNodeY;
	}
	
	public ArrayList getNodes(){
		return this.nodeMapArrList;
    }
	
   //loop through all the nodes until you find id
    public Node getNodeById(int id){
	    Node aNode = null;
	    
    	for(int i = 0; i< nodeMapArrList.size(); i++){
    		ArrayList al = (ArrayList) this.nodeMapArrList.get(i);
    		for(int k = 0; k < al.size(); k++)
    		{
    			aNode = (Node) al.get(k);
    			if(aNode.getNodeId() == id){
    				return aNode;		
    			}
    		}
			
    	}	
    	return aNode;
	   	 
    }
	
	public Node getNode(int x, int y){
		return (Node) ((ArrayList) this.nodeMapArrList.get(x)).get(y);
	}
	
	/* *
	 * Return set start node
	 * */
	public Node getStartNode(){
		return (Node) ((ArrayList) this.nodeMapArrList.get(this.startNodeX)).get(this.startNodeY);
	}
	
	
	
	/* *
	 * @return Goal point 
	 * */
	public Point getGoalPoint(){
		return new Point(this.goalNodeX, this.goalNodeY);
	}
	
	/* *
	 * @return Node Goal 
	 * */
	public Node getGoalNode() {
		  
          return (Node) ((ArrayList) this.nodeMapArrList.get(this.goalNodeX)).get(this.goalNodeY);

  }
	

}
