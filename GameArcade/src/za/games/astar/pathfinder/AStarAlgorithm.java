/*
''-----------------------------------------------------
' Created by: Carene Smith
' Created on: 7 October 2013
' Description: The class that does the calculation to get the shortest path
'        	   from the start to the goal node.  
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

package za.games.astar.pathfinder;


import java.awt.Point;
import java.util.*;


import za.games.astar.calculator.ManhattenDistanceCalculator;
import za.games.astar.utils.LogUtil;

public class AStarAlgorithm {
	private TerrainMap terrainMap ;
	private ManhattenDistanceCalculator mDistCal ;
	private ArrayList closedList;
	private ArrayList openList;
	private ArrayList shortestPath;
	private Comparator comparator;
	
	public AStarAlgorithm(TerrainMap map,ManhattenDistanceCalculator mdCal ){
		terrainMap = map;
		mDistCal = mdCal;
		
		openList = new ArrayList();
		closedList = new ArrayList();
	}
	
	/*
	 * if neighbour NOT in ignoreList
	 * For each neighbour, calculate distance from tile to Goal (Manhatten)
	 * add to costSoFar to get the FScore (the trick here is that you set the distanceFromStart correctly
	 * 
	 * Sort on FScore
	 * 
	 * Problem is if you have fscores that are the same
	 * Get the cheapest Node at that point,compare with current neighbour bases on Y and then X values
	 * 
	 * 
	 * */
	private Node getLowestFScoreNode(ArrayList neighbour, ArrayList ignoreList, Node current){
		Node aNode;
		int costSoFar =0;
		int cost = 0;
		int fScore = 0;
		int currF = 0;
		int bestF = 0;
		int bestNodeId = 1;
		boolean bContinueSpecialCase = false;
		
		//ArrayList listWithFScores = new ArrayList();
		//comparator = new FCostComparator();
		
		costSoFar = current.getDistanceFromStart();
		LogUtil.debug(" ------------");
		LogUtil.debug(" costSoFar = " + costSoFar + " ,x:"+current.getXCoordinate() + ":y" + current.getYCoordinate());
		Node bestChoiceNode = null ;
		for(int a = 0; a< neighbour.size(); a++){
			aNode = (Node) neighbour.get(a);
			if(ignoreList.contains(aNode)){
				if(a==0){
					bContinueSpecialCase = true;
				}
				continue;
			}
			
			
			cost = costSoFar + aNode.getMoveCost();
			fScore = cost + mDistCal.getEstDistanceToGoal(aNode.getPoint(),terrainMap.getGoalPoint() );
			
			
			LogUtil.debug("neighbour.size()  =" + neighbour.size() + ", a = " + a + ",fScore = " + fScore + ": prevF = " + bestF );
			if(neighbour.size() == 1){
				bestChoiceNode = aNode;
			}else{
				if((a>0 || (a == neighbour.size()-1)) && (!bContinueSpecialCase)){
					if(fScore < bestF){
						bestChoiceNode = aNode;
						bestF = fScore;
						bestNodeId = aNode.getNodeId();
					}else if (bestF == fScore){
						//current is aNode
						Node prevBest = terrainMap.getNodeById(bestNodeId);
						if( (aNode.getYCoordinate() > prevBest.getYCoordinate() )){
							bestChoiceNode =  aNode;
						}else if(aNode.getXCoordinate() > prevBest.getXCoordinate()) {
							bestChoiceNode =  aNode;
							bestF = fScore;
							bestNodeId = aNode.getNodeId();
							
						}else{
							bestChoiceNode = prevBest;
							bestF = fScore;
							bestNodeId = prevBest.getNodeId();
						}
						LogUtil.debug("Choose between ");
						LogUtil.debug(" Current = " + aNode.toString());
						LogUtil.debug("Prev" + prevBest.toString());
						LogUtil.debug("winner is ");
						LogUtil.debug(bestChoiceNode.toString());
						
					}	
						
				}else{
					bestChoiceNode = aNode;
					bestF = costSoFar + aNode.getMoveCost() + mDistCal.getEstDistanceToGoal(aNode.getPoint(),terrainMap.getGoalPoint() );  //current.getFScore();
					bestNodeId = aNode.getNodeId();
					bContinueSpecialCase = false;
				}
			}
		}	

		bestChoiceNode.setParentNode(current);
		bestChoiceNode.setDistanceFromStart(costSoFar + bestChoiceNode.getMoveCost());
		bestChoiceNode.setFScore(costSoFar + bestChoiceNode.getMoveCost() + mDistCal.getEstDistanceToGoal(bestChoiceNode.getPoint(),terrainMap.getGoalPoint() )); 
		bestChoiceNode.setManhattenDistanceToGoal( mDistCal.getEstDistanceToGoal(bestChoiceNode.getPoint(),terrainMap.getGoalPoint() ));
		LogUtil.debug("Best choice =  bestF = " + bestF + " -  " + bestChoiceNode.toString() );		
		return bestChoiceNode;
		
	}
	
	
   /*
    * Calculate the shortest path from stat to goal, using the Manhatten distance calculator
    * */
	public ArrayList calculateShortestPath(int startX, int startY, int gaolX, int goalY) throws Exception{
		
		ArrayList adjacentNodes ;
		Node aNode;
		Node current;
		 
		Node startNode = terrainMap.getStartNode();
		
		//Make sure you have a clean start
		closedList.clear();   // The set of nodes already evaluated.
		
		//As we just began, set current as node start
        current = startNode;
        closedList.add(current);
        
        while(!current.equals(terrainMap.getGoalNode())){
        	Node lowestNeighbour = getLowestFScoreNode(current.getAdjacentNodeList(), closedList, current); 
        	current = lowestNeighbour;
        	if(current == null)
    	    	throw new Exception("Could not determine current Node");
        	
        	closedList.add(current);
        }
        
        return reconstructPath(current);
		
	}
	
	/*
	 * Rewalk the path from current node, back to its parent until you 
	 * reached the start node. This will reflect the best & shortest path
	 * which will be printed as the output file.
	 * */ 
	private ArrayList reconstructPath(Node node) {
		 
         ArrayList path = new ArrayList();

         while(!(node.getParentNode() == null)) {
        	 
                 path.add(0,node.getPoint());
                 if(node.getParentNode().getManhattenDistanceToGoal() == node.getManhattenDistanceToGoal()){
            		 node = node.getParentNode().getParentNode();
            	 }else{
            		 node = node.getParentNode();
         		}
         }
         
         //add start
         path.add(0, terrainMap.getStartNode().getPoint());
         this.shortestPath = path;

         return path;
 }
	 	 
}

/*
 * Inner class to sort the adjacent nodes by the lowest fScore.
 * 
 * */
class FCostComparator implements Comparator{
	

 	public int compare(Object newlyAdded, Object old) {
 		if(newlyAdded instanceof Node ){
 			Node newNode = (Node) newlyAdded;
 			Node oldNode = (Node) old;
 			
 			if(newNode.getFScore() == oldNode.getFScore()){
 				//check for highest y and highest x
 				if(newNode.getYCoordinate() > oldNode.getYCoordinate()){
 					return -1;
 				}else if ( (newNode.getYCoordinate() == oldNode.getYCoordinate() ) && newNode.getXCoordinate() > oldNode.getYCoordinate()){
 					return -1;
 				}else if(newNode.getYCoordinate() < oldNode.getYCoordinate()){
 					return 1;
 				}else{
 					return 0;
 				}
 					
 				
 			}
 			
 			if(newNode.getFScore() < oldNode.getFScore()){
 				return -1;
 			}
 			
 			if(newNode.getFScore() > oldNode.getFScore()){
 				return 1;
 			}
 		}
 		return 0;
 	}
 }	

