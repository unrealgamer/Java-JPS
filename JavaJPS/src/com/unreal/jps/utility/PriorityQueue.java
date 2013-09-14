package com.unreal.jps.utility;

/**
 *
 * @author Shane
 */
public interface PriorityQueue {
    /**
     * Insert into the queue maintaining the sort order
     * @param comp the item to insert
     */
    public void add(Comparable comp);
    
    /**
     * Removes the smallest item from the queue
     * @return the item removed 
     */
    public Comparable pop();
    
    /**
     * Retrieves the smallest item from the queue
     * @return the item found
     */
    public Comparable pull();
    
    /**
     * Check if the queue is empty
     * @return true if empty, false otherwise
     */
    public boolean isEmpty();
    
    /**
     * Clears the queue
     */
    public void clear();
    
    /**
     * Gets the current size of the queue
     * @return the size of the queue
     */
    public int size();
}//end interface
