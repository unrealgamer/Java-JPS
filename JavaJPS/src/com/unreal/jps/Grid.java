package com.unreal.jps;

import java.io.File;
import java.io.FileNotFoundException;
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
    
    public Grid(int width, int height)
    {
        this.myNodes = new GridNode[width][height];
        try {
            readGrid("C:\\Users\\Shane\\Desktop\\grid.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Grid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public GridNode getNode(int x, int y)
    {
        return this.myNodes[x][y];
    }
    
    public boolean isPassable(int x, int y)
    {
        return this.myNodes[x][y].isPassable();
    }
    
    public void readGrid(String fileName) throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(fileName));
        
        int width = in.nextInt();
        int height = in.nextInt();
        
        this.myNodes = new GridNode[width][height];
        
        int r = 0, c = 0;
        
        while(in.hasNextInt())
        {
            int type = in.nextInt();
            if(type == 2) { //Start
                this.myNodes[r][c] = new GridNode(r,c,true, null);
                this.startNode = this.myNodes[r][c];
            }
            else if(type == 3) { //end
                this.myNodes[r][c] = new GridNode(r,c,true, null);
                this.endNode = this.myNodes[r][c];
            }
            else if(type == 0) {
                this.myNodes[r][c] = new GridNode(r, c, true, null);
            }
            else
                this.myNodes[r][c] = new GridNode(r, c, false, null);
            c++;
            if(c >= width)
            {
                c = 0;
                r++;
            }
        }
        
    }
    
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
    }
    
}
