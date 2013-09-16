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
public class PathFinderTwo {
    
    private Grid myGrid;
    private GridNode startNode, endNode;
    private PriorityQueue openList;
    private ArrayList<GridNode> closedList;
    
    public PathFinderTwo(Grid grid, GridNode startNode, GridNode endNode)
    {
        String test = "test";
        this.myGrid = grid;
        this.startNode = startNode;
        this.endNode = endNode;
    }
    
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
            
            addNeighbors(curNode, openList, true);
            myGrid.printPath(backTrace(curNode));
        }
        
        return null;
    }
    
    private void addNeighbors(GridNode node, PriorityQueue openList, boolean cutCorners)
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
    }
    
    private void calculateNodeScore(GridNode node)
    {
        int manhattan = Math.abs(endNode.getX() - node.getX()) * 10 + Math.abs(endNode.getY() - node.getY()) * 10;
        
        node.setHScore(manhattan);
        
        GridNode parent = (GridNode)node.getParent();
        
        node.setGScore(parent.getGScore() + calcuateGScore(node, parent));   
    }
    
    private int calcuateGScore(GridNode newNode, GridNode oldNode)
    {
        int dx = newNode.getX() - oldNode.getX();
        int dy = newNode.getY() - oldNode.getY();
        
            if(dx == 0 || dy == 0)
                return Math.abs(10 * Math.max(dx, dy));
            else
                return Math.abs(14 * Math.max(dx, dy));
    }
    
    
    public ArrayList<GridNode> jumpPointSearch()
    {
        closedList = new ArrayList<>();
        openList = new BinaryHeap(startNode);
        
        while(!openList.isEmpty())
        {
            GridNode curNode = (GridNode)openList.pop();
            
            if(curNode.equals(endNode))
                return backTrace(curNode);
            
            closedList.add(curNode);
            
            identifySuccessors(curNode);
            
        }

        return null;
    }
    
    private void identifySuccessors(GridNode curNode)
    {
        for(int dx = -1; dx <= 1; dx++)
        {
            for(int dy = -1; dy <= 1; dy++)
            {
                if(dx == 0 && dy == 0)
                    continue;
                
                if(isValidNeighbor(curNode, myGrid.getNode(curNode.getX() + dx, curNode.getY() + dy)))
                {
                    GridNode jumpNode = jump(curNode, dx, dy);
                    if(jumpNode != null)
                    {
                        if(!openList.contains(jumpNode) && !closedList.contains(jumpNode))
                        {
                            jumpNode.setParent(curNode);
                            calculateNodeScore(jumpNode);
                            openList.add(jumpNode);
                        }
                    }
                }
                
            }
        }
    }
    
    private GridNode jump(GridNode curNode, int dx, int dy)
    {
        int nextX = curNode.getX() + dx;
        int nextY = curNode.getY() + dy;
        GridNode nextNode = myGrid.getNode(nextX, nextY);
        
        if(nextNode == null || !nextNode.isPassable()) return null;
        
        if(nextNode.equals(endNode)) return nextNode;
        
        if(dx != 0 && dy != 0)
        {
            //If neighbors do exist and are forced (left and right impassable)
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
        else
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
        return jump(nextNode, dx, dy);
    }
    
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
    }
    
    private boolean isValidNeighbor(GridNode node, GridNode neighbor)
    {
        return neighbor != null && neighbor.isPassable() && !closedList.contains(neighbor) && !neighbor.equals(node);
    }
    
}
