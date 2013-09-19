/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps;

import com.unreal.jps.utility.BinaryHeap;
import com.unreal.jps.utility.PriorityQueue;
import java.util.ArrayList;


/**
 *
 * @author Shane
 */
public class PathFinder {
    
    private Grid myGrid;
    private GridNode startNode, endNode;
    private PriorityQueue openList;
    private ArrayList<GridNode> closedList;
    
    public PathFinder(Grid grid, GridNode startNode, GridNode endNode)
    {
        this.myGrid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
    }
    
    /**
     * Finds the shortest path using the A* algorithm 
     * @return A path of nodes, or null if no path exists
     */
    public ArrayList<GridNode> aStarPathFind()
    {
        closedList = new ArrayList<>();
        openList = new BinaryHeap(startNode);
        
        while(!openList.isEmpty())
        {
            GridNode curNode = (GridNode)openList.pop();
            
            if(curNode.equals(endNode))
                return backTrace(curNode);
            
            closedList.add(curNode);
            
            addNeighbors(curNode, true);
        }
        return null;
    }//end aStarPathFind
    
    /**
     * Used by the A* search to add each nodes neighbors to the open list if conditions are met
     * @param node The node to find neighbors for
     * @param cutCorners Will we cut corners or go around them (longer paths if false)
     */
    private void addNeighbors(GridNode node, boolean cutCorners)
    {
        int x = node.getX(); int y = node.getY();
        for(int dy = -1; dy <= 1; dy++) {
            for(int dx = 1; dx >= -1; dx--) {
                GridNode neighbor = myGrid.getNode(x + dx, y + dy);
                if(isValidNeighbor(node, neighbor)) 
                {
                    if(!(dx != 0 && dy != 0) || cutCorners)
                    {
                        if(!openList.contains(neighbor))
                        {
                            neighbor.setParent(node);
                            calculateNodeScore(neighbor);
                            openList.add(neighbor);
                        }
                        else if(calcuateGScore(node, neighbor) + node.getGScore() < neighbor.getGScore()) //If the path through us is better
                        {
                            neighbor.setParent(node);
                            calculateNodeScore(neighbor);
                        }
                    }
                }
            }
        }
    }//end addNeighbors
    
    /**
     * Calculates the given node's score using the Manhattan distance formula
     * @param node The node to calculate
     */
    private void calculateNodeScore(GridNode node)
    {
        int manhattan = Math.abs(endNode.getX() - node.getX()) * 10 + Math.abs(endNode.getY() - node.getY()) * 10;
        
        node.setHScore(manhattan);
        
        GridNode parent = (GridNode)node.getParent();
        
        node.setGScore(parent.getGScore() + calcuateGScore(node, parent));   
    }//end calculateNodeScore
    
    /**
     * Will calculate the gScore between two nodes
     * @param newNode
     * @param oldNode
     * @return The gScore between the nodes
     */
    private int calcuateGScore(GridNode newNode, GridNode oldNode)
    {
        int dx = newNode.getX() - oldNode.getX();
        int dy = newNode.getY() - oldNode.getY();
        
            if(dx == 0 || dy == 0) //Horizontal or vertical
                return Math.abs(10 * Math.max(dx, dy));
            else                   // Diaganol
                return Math.abs(14 * Math.max(dx, dy));
    }//end calculateGScore
    
    /**
     * Finds the shortest path between two nodes using the Jump Point Search Algorithm
     * @return A path of nodes
     */
    public ArrayList<GridNode> jumpPointSearch()
    {
        closedList = new ArrayList<>();
        openList = new BinaryHeap(startNode);
        
        while(!openList.isEmpty())
        {
            GridNode curNode = (GridNode)openList.pop(); //Get the next node with the lowest fScore
            
            if(curNode.equals(endNode)) //If this node is the end node we are done
                return backTrace(curNode); // Return the current node to back trace through to find the path
            
            identifySuccessors(curNode);    //Find the successors to this node (add them to the openList)
            
            closedList.add(curNode);    //Add the curnode to the closed list (as to not open it again)
        }

        return null;
    }//calculate jumpPointSearch
    
    /**
     * Finds the successors the the curNode and adds them to the openlist
     * @param curNode The current node to search for
     */
    private void identifySuccessors(GridNode curNode)
    {
        //Use two for loops to cycle through all 8 directions
        for(int dx = -1; dx <= 1; dx++) 
        {
            for(int dy = -1; dy <= 1; dy++)
            {
                if(dx == 0 && dy == 0) //Skip the curNode 
                    continue;
                
                //If the neighbor exists at this direction and is valid contiue
                if(isValidNeighbor(curNode, myGrid.getNode(curNode.getX() + dx, curNode.getY() + dy)))
                {
                    //Try to find a jump node (One that is further down the path and has a forced neighbor)
                    GridNode jumpNode = jump(curNode, dx, dy);
                    if(jumpNode != null)
                    {
                        //If we found one add it to the open list if its not already on it
                        if(!openList.contains(jumpNode) && !closedList.contains(jumpNode))
                        {
                            jumpNode.setParent(curNode); //Set its parent so we can find our way back later
                            calculateNodeScore(jumpNode);   //Calculate its score to pull it from the open list later
                            openList.add(jumpNode);     // Add it to the open list for continuing the path
                        }
                    }
                }
                
            }
        }
    }//end identifySuccessors
    
    /**
     * Finds the next node heading in a given direction that has a forced neighbor or is significant
     * @param curNode The node we are starting at
     * @param dx The x direction we are heading
     * @param dy The y direction we are heading
     * @return The node that has a forced neighbor or is significant
     */
    private GridNode jump(GridNode curNode, int dx, int dy)
    {
        //The next nodes details
        int nextX = curNode.getX() + dx;
        int nextY = curNode.getY() + dy;
        GridNode nextNode = myGrid.getNode(nextX, nextY);
        
        // If the nextNode is null or impassable we are done here
        if(nextNode == null || !nextNode.isPassable()) return null;
        
        //If the nextNode is the endNode we have found our target so return it
        if(nextNode.equals(endNode)) return nextNode;
        
        //If we are going in a diaganol direction check for forced neighbors
        if(dx != 0 && dy != 0)
        {
            //If neighbors do exist and are forced (left and right are impassable)
            if(myGrid.getNode(nextX - dx, nextY) != null && myGrid.getNode(nextX - dx, nextY + dy) != null)
                if(!myGrid.getNode(nextX - dx, nextY).isPassable() && myGrid.getNode(nextX - dx, nextY + dy).isPassable())
                    return nextNode;
            
            //If neighbors do exist and are forced (top and bottom impassable)
            if(myGrid.getNode(nextX, nextY - dy) != null && myGrid.getNode(nextX + dx, nextY - dy) != null)
                if(!myGrid.getNode(nextX, nextY - dy).isPassable() && myGrid.getNode(nextX + dx, nextY - dy).isPassable())
                    return nextNode;
            
            if(jump(nextNode, dx, 0) != null || jump(nextNode, 0, dy) != null)//Special Diagonal Case
                return nextNode; 
        }
        else //We are going horizontal or vertical
        {
            if(dx != 0) //Horizontal Case
            {
                if(myGrid.isPassable(nextX + dx, nextY) && !myGrid.isPassable(nextX, nextY + 1))
                    if(myGrid.isPassable(nextX + dx, nextY + 1))
                        return nextNode;
                
                if(myGrid.isPassable(nextX + dx, nextY) && !myGrid.isPassable(nextX, nextY - 1))
                    if(myGrid.isPassable(nextX + dx, nextY - 1))
                        return nextNode;
            }
            else        //Vertical Case
            {
                if(myGrid.isPassable(nextX, nextY + dy) && !myGrid.isPassable(nextX + 1, nextY))
                    if(myGrid.isPassable(nextX + 1, nextY + dy))
                        return nextNode;
                
                if(myGrid.isPassable(nextX, nextY + dy) && !myGrid.isPassable(nextX - 1, nextY))
                    if(myGrid.isPassable(nextX - 1, nextY + dy))
                        return nextNode;
            }
        }
        return jump(nextNode, dx, dy); //No forced neighbors so we are continuing down the path
    }//end jump
    
    /**
     * Goes through each parent of each subsequent node and adds them to a list 
     * starting with the provided node
     * @param theNode
     * @return The list of nodes that make up the path
     */
    private ArrayList<GridNode> backTrace(GridNode theNode)
    {
        ArrayList<GridNode> thePath = new ArrayList<>();
        GridNode parent = theNode;
        while(parent != null)
        {
            thePath.add(parent);
            parent = (GridNode)parent.getParent();
        }
        return thePath;
    }//end backTrace
    
    /**
     * Check if the neighbor of the given node is valid
     * @param node The node to check
     * @param neighbor The neighbor of the node to check
     * @return True if neighbor is valid, false otherwise
     */
    private boolean isValidNeighbor(GridNode node, GridNode neighbor)
    {
        return neighbor != null && neighbor.isPassable() && !closedList.contains(neighbor) && !neighbor.equals(node);
    }//end isValidNeighbor
    
}//end PathFinder
