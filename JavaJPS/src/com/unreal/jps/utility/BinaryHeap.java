/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps.utility;

import java.util.ArrayList;

/**
 *
 * @author Shane
 */
public class BinaryHeap implements PriorityQueue {

    private BinaryHeap.ArrayListOne<Comparable> myList;
    
    /**
     * Default constructor; creates a new empty list
     */
    public BinaryHeap()
    {
        myList = new BinaryHeap.ArrayListOne<>();
    }//end constructor
    
    /**
     * Creates a new heap with a starting item
     * @param initialNode 
     */
    public BinaryHeap(Comparable initialNode)
    {
        myList = new BinaryHeap.ArrayListOne<>();
        myList.add(1, initialNode);
    }//end copnstructor
    
    @Override
    public void add(Comparable comp) {
        if(size() == 0) {
            myList.add(1, comp);
            return;
        }
        
        int index = size() + 1;
        
        myList.add(index, comp);
        
        int parentIndex = index / 2;
        
        while(myList.get(parentIndex).compareTo(comp) > 0)
        {
            swap(index, parentIndex);
            
            if(parentIndex == 1) //Check if we just swapped with the top node
                break;
            
            index = parentIndex;
            parentIndex = index / 2;
        }
    }

    @Override
    public Comparable pop() {
        
        if(myList.size() == 1)
            return myList.remove(size());
        
        Comparable retValue = pull();

        myList.set(1, myList.remove(size()));
        
        int index = 1, ch1i = 2, ch2i = 3;
        
        while(true)
        {
            int swapi = -1;
            
            if(ch2i <= size()) //If there are two children then find the smallest
            {
                swapi = (myList.get(index).compareTo(myList.get(ch1i)) > 0) ? ch1i : swapi; //is 1st child smaller then us?
                swapi = (myList.get(ch1i).compareTo(myList.get(ch2i)) > 0) ? ch2i : swapi; //is 2nd child smaller then 1st?
            }
            else if(ch1i <= size()) //If there is only one child
            {
                swapi = (myList.get(index).compareTo(myList.get(ch1i)) > 0) ? ch1i : swapi; //is it samller then us?
            }
            if(swapi != -1) //If any children where smaller then swap and adjust for next iteration
            {
                swap(swapi, index);
                index = swapi;
                ch1i = index*2;
                ch2i = ch1i+1;
            }
            else
                break; //There are no children or none that are smaller than us
        }
        return retValue;
    }

    @Override
    public Comparable pull() {
        return myList.get(1);
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public void clear() {
        myList.clear();
    }

    @Override
    public int size() {
        return myList.size();
    }
    
    @Override
    public boolean contains(Comparable comp)
    {
        return myList.contains(comp);
    }
    
    /**
     * Swaps the elements at position idx1 and idx2
     * @param idx1 first index
     * @param idx2 second index
     */
    private void swap(int idx1, int idx2)
    {
        Comparable temp = myList.get(idx1);
        myList.set(idx1, myList.get(idx2));
        myList.set(idx2, temp);
    }
    
    /**
     * Custom ArrayList with an index starting at 1 instead of zero
     */
    class ArrayListOne<T> extends ArrayList<T>
    {
        @Override
        public void add(int index, T value) {
            super.add(index - 1, value);
        }
        @Override
        public T get(int index) {
            return super.get(index - 1);
        }
        @Override
        public T set(int index, T value){
            return super.set(index - 1, value);
        }
        
        @Override
        public T remove(int index) {
            return super.remove(index - 1);
        }
    }
    
    @Override
    public String toString()
    {
        String out = "";
        for(Comparable i : myList)
            out += i.toString() + " ";
        return out.substring(0, out.length()-1);
    }
    
}
