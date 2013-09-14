package com.unreal.jps;

/**
 *
 * @author Shane
 */
public class GridNode extends Node{

    private int myX, myY;
    
    public GridNode(int x, int y)
    {
        this.myX = x;
        this.myY = y;
    }
    
    public GridNode(int x, int y, Node parent)
    {
        this.myX = x; 
        this.myY = y;
        this.setParent(parent);
    }
    
    public void setLocation(int x, int y) {
        this.myX = x; this.myY = y;
    }
    
    public int getX() {
        return this.myX;
    }
    
    public int getY() {
        return this.myY;
    }
    
    @Override
    public int getFScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}