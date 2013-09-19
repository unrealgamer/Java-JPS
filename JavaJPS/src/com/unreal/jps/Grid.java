package com.unreal.jps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shane
 */
public class Grid {
    
    private GridNode[][] myNodes;
    private GridNode startNode;
    private GridNode endNode;
    private PathFinder pathFinder;
    /**
     * Default Constructor
     * @param width The Width of the grid
     * @param height The height of the grid
     */
    public Grid(int width, int height)
    {
        this.myNodes = new GridNode[width][height];
        try {
            readGrid("grid.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
        pathFinder = new PathFinder(this, startNode, endNode);
        printPath(pathFinder.jumpPointSearch());
        printPath(pathFinder.aStarPathFind());
    }//end constructor
    
    /**
     * Gets a node at the specified location in the grid
     * @param x
     * @param y
     * @return The node at the x,y coordinates
     */
    public GridNode getNode(int x, int y)
    {
        if(x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0)
            return null;
        return this.myNodes[x][y];
    }//end getNode
    
    /**
     * Checks if a node is passable at the specified location in the grid
     * @param x
     * @param y
     * @return True if passable, false otherwise
     */
    public boolean isPassable(int x, int y)
    {
        if(x >= this.myNodes.length || x < 0 || y >= this.myNodes[0].length || y < 0)
            return false;
        return this.myNodes[x][y].isPassable();
    }//end isPassable
    
    /**
     * Retrieves the starting node for the path
     * @return 
     */
    public GridNode getStart()
    {
        return startNode;
    }//end getStart
    
    /**
     * Retrieves the ending node for the path
     * @return 
     */
    public GridNode getEnd()
    {
        return endNode;
    }//end getEnd
    
    
    /**
     * Reads a file and creates a grid based on the data in the file
     * @param fileName The location of the file to read
     * @throws FileNotFoundException 
     */
    public void readGrid(String fileName) throws FileNotFoundException
    {   Scanner in = new Scanner(new File(fileName));
        
        int width = in.nextInt();
        int height = in.nextInt();
        
        this.myNodes = new GridNode[width][height];
        
        int r = 0, c = 0;
        
        while(in.hasNextInt())
        {
            int type = in.nextInt();
            if(type == 2) { //Start
                this.myNodes[r][c] = new GridNode(r,c,true);
                this.startNode = this.myNodes[r][c];
            }
            else if(type == 3) { //end
                this.myNodes[r][c] = new GridNode(r,c,true);
                this.endNode = this.myNodes[r][c];
            }
            else if(type == 0) {
                this.myNodes[r][c] = new GridNode(r, c, true);
            }
            else
                this.myNodes[r][c] = new GridNode(r, c, false);
            r++;
            if(r >= width)
            {
                r = 0;
                c++;
            }
        }
        
    }//end readGrid
 
    /**
     * Prints the grid with the path highlighted with "X"s at each node that includes the path
     * @param thePath The path to print
     */
    public void printPath(ArrayList<GridNode> thePath)
    {
        String out = "";
        for(int r = 0; r < this.myNodes[0].length; r++)
        {
            GridNode[] row = this.myNodes[r];
            for(int c = 0; c < this.myNodes.length; c++)
            {
                GridNode node = getNode(c, r);
                if(node.equals(startNode)) {
                    out += "2 ";
                    continue;
                }
                else if(node.equals(endNode)) {
                    out += "3 ";
                    continue;
                }
                if(node.isPassable())
                    if(!thePath.contains(node))
                        out += "0 ";
                    else
                        out += "x ";
                else
                    out += "1 ";
            }
            out += "\n";
        }
        System.out.println(out);
    }//end printPath
    
    /**
     * To string method for this class. It just prints the grid
     * @return 
     */
    public String toString()
    {
        String out = "";
        for(int r = 0; r < this.myNodes.length; r++)
        {
            GridNode[] row = this.myNodes[r];
            for(GridNode node : row)
            {
                if(node.equals(startNode)) {
                    out += "2 ";
                    continue;
                }
                else if(node.equals(endNode)) {
                    out += "3 ";
                    continue;
                }
                if(node.isPassable())
                    out += "0 ";
                else
                    out += "1 ";
            }
            out += "\n";
        }
        return out;
    }//end toString
    
}//end Grid
