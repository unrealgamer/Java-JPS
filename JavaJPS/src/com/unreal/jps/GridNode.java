package com.unreal.jps;

/**
 *
 * @author Shane
 */
public class GridNode extends Node{

    private int myX, myY;
    private int gScore, hScore;
    
    /**
     * Default constructor
     * @param x X coordinate
     * @param y y coordinate
     * @param isPassable Is the node passable
     */
    public GridNode(int x, int y, boolean isPassable)
    {
        this.myX = x;
        this.myY = y;
        this.setPassable(isPassable);
        this.gScore = 0;
        this.hScore = 0;
    }//end constructor
    
    /**
     * Sets this nodes location
     * @param x
     * @param y 
     */
    public void setLocation(int x, int y) {
        this.myX = x; this.myY = y;
    }//end setLocation
    
    /**
     * Gets this nodes x coordinate
     * @return x coordinate
     */
    public int getX() {
        return this.myX;
    }//end getX
    
    /**
     * Gets this node's y coordinate
     * @return y coordinate
     */
    public int getY() {
        return this.myY;
    }//end getY
    
    @Override
    public int getFScore() {
        return hScore + gScore;
    }//end getFScore
    
    /**
     * Sets this nodes heuristic score
     * @param hscore 
     */
    public void setHScore(int hscore)
    {
        this.hScore = hscore;
    }//end setHScore
    
    /**
     * Sets this node's gScore
     * @param gScore 
     */
    public void setGScore(int gScore)
    {
        this.gScore = gScore;
    }//end setGScore
    
    /**
     * Gets this node's Gscore
     * @return 
     */
    public int getGScore()
    {
        return this.gScore;
    }//end getGScore
    
    /**
     * Standard toString method
     * @return 
     */
    public String toString()
    {
        return "X: " + this.myX + " Y: " + this.myY + " F: " + getFScore() + " G: " + getGScore();
    }//end toString
    
}//end GridNode
