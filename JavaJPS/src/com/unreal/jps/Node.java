package com.unreal.jps;

/**
 *
 * @author Shane
 */
public abstract class Node implements Comparable{
    
    private Node myParentNode;
    
    private boolean isPassible = true;
    
    /**
     * Gets this nodes FScore
     * @return the FScore
     */
    public abstract int getFScore();
    
    /**
     * Gets this node's parent
     * @return the parent node
     */
    public Node getParent()
    {
        return this.myParentNode;
    }//end getParent
    
    /**
     * Sets this node's parent
     * @param parentNode the parent node
     */
    public void setParent(Node parentNode)
    {
        this.myParentNode = parentNode;
    }//end setParent
    
    public boolean isPassible()
    {
        return this.isPassible;
    }
    
    public void setPassible(boolean val)
    {
        this.isPassible = val;
    }
    
    @Override
    public int compareTo(Object t) {
        if(!(t instanceof Node))
            return 0;
        Node other = (Node)t;
        if(getFScore() < other.getFScore())
            return -1;
        else if(getFScore() > other.getFScore())
            return 1;
        else return 0;
    } 
}
