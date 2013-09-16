package com.unreal.jps;

/**
 *
 * @author Shane
 */
public class GridNode extends Node{

    private int myX, myY;
    private int gScore, hScore;
    
    public GridNode(int x, int y, boolean isPassable)
    {
        this.myX = x;
        this.myY = y;
        this.setPassable(isPassable);
        this.gScore = 0;
        this.hScore = 0;
    }
    
    public GridNode(int x, int y, boolean isPassable, Node parent)
    {
        this.myX = x; 
        this.myY = y;
        this.setPassable(isPassable);
        this.setParent(parent);
        this.gScore = 0;
        this.hScore = 0;
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
        return hScore + gScore;
    }
    
    public void setHScore(int hscore)
    {
        this.hScore = hscore;
    }
    
    public void setGScore(int gScore)
    {
        this.gScore = gScore;
    }
    
    public int getGScore()
    {
        return this.gScore;
    }
    
    public String toString()
    {
        return "X: " + this.myX + " Y: " + this.myY + " F: " + getFScore() + " G: " + getGScore();
    }
    
}
