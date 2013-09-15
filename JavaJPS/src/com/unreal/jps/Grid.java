package com.unreal.jps;

/**
 *
 * @author Shane
 */
public class Grid {
    
    private GridNode[][] myNodes;
    
    public Grid(int width, int height)
    {
        this.myNodes = new GridNode[width][height];
    }
    
    public GridNode getNode(int x, int y)
    {
        return this.myNodes[x][y];
    }
    
    public boolean isPassable(int x, int y)
    {
        return this.myNodes[x][y].isPassable();
    }
    
}
