package com.unreal.jps;

import com.unreal.jps.utility.BinaryHeap;
import com.unreal.jps.utility.PriorityQueue;

/**
 *
 * @author Shane
 */
public class Program {
    
    /**
     * Starts the program by creating a new grid that automatically path finds
     * @param args 
     */
    public static void main(String[] args)
    {
       Grid g = new Grid(5, 5);
    }//end Main
    
    /**
     * Just testing out the PriorityQueue and BinaryHeap stack classes. (Can be removed)
     */
    private static void pqTests()
    {
        PriorityQueue pq = new BinaryHeap();
        pq.add(10); System.out.println(pq);
        pq.add(30); System.out.println(pq);
        pq.add(20); System.out.println(pq);
        pq.add(34); System.out.println(pq);
        pq.add(38); System.out.println(pq);
        pq.add(30); System.out.println(pq);
        pq.add(24); System.out.println(pq);
        pq.add(17); System.out.println(pq);
        pq.add(10); System.out.println(pq);
        pq.add(10); System.out.println(pq);
        pq.add(30); System.out.println(pq);
        pq.add(20); System.out.println(pq);
        pq.add(34); System.out.println(pq);
        pq.add(38); System.out.println(pq);
        pq.add(30); System.out.println(pq);
        pq.add(24); System.out.println(pq);
        pq.add(17); System.out.println(pq);
        System.out.println("Popped Value: " + pq.pop()); System.out.println("New Queue: " + pq);
        System.out.println("Popped Value: " + pq.pop()); System.out.println("New Queue: " + pq);
        System.out.println("Popped Value: " + pq.pop()); System.out.println("New Queue: " + pq);
        System.out.println("Popped Value: " + pq.pop()); System.out.println("New Queue: " + pq);
        System.out.println("Popped Value: " + pq.pop()); System.out.println("New Queue: " + pq);
    }//end pqTests
    
}//end Program
