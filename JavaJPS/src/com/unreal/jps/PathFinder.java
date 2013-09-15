/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps;

import com.unreal.jps.utility.BinaryHeap;
import com.unreal.jps.utility.PriorityQueue;
import java.util.ArrayList;

/**
 *
 * @author Adrian
 */
public class PathFinder {

    
    public static void jumpPointSearch(Grid theGrid, int startX, int startY, int endX, int endY)
    {
        PriorityQueue openList = new BinaryHeap();
        
        GridNode startNode = theGrid.getNode(startX, startY);
        GridNode endNode = theGrid.getNode(endX, endY);
        
        openList.add(startNode);
        
        while(!openList.isEmpty())
        {
            GridNode node = (GridNode)openList.pop();
            
            if(node.equals(endNode))
                return;
            
            findSuccessors(node, theGrid);
        }
    }
    
    public static void findSuccessors(GridNode node, Grid theGrid)
    {
        ArrayList<GridNode> neighbors = findNeighbors(node, theGrid);
    }
    
    public static ArrayList<GridNode> findNeighbors(GridNode node, Grid theGrid)
    {
        ArrayList<GridNode> neighbors = new ArrayList<>();
        
        int x = node.getX(), y = node.getY();
        
        GridNode parentNode = (GridNode)node.getParent();
        
        
        if(parentNode != null)
        {
            int px = parentNode.getX(), py = parentNode.getY();
            
            int dx = (x - px) / Math.abs(x - px);
            int dy = (y - py) / Math.abs(y - py);
            
            if(dx != 0 && dy != 0)
            {
                if(theGrid.isPassible(x, y + dy))
                    neighbors.add(theGrid.getNode(x, y + dy));
                if(theGrid.isPassible(x + dx, y))
                    neighbors.add(theGrid.getNode(x + dx, y));
            }
            
        }
        
        return null;
        
    }
    
    /*
    public static GridNode[] findForcedNeighbor(GridNode origin, Vector2 direction) {
        //Grid myGrid = Program.getGrid();
        //GridNode[] pathNodes = new GridNode[2];
        if (direction.x == 0) {
            //Direction is vertical
            //if myGrid.getCell(origin.x){}
        } else if (direction.y == 0) {
            //Direction is horizontal
        }

        return pathNodes;
    }*/
}
