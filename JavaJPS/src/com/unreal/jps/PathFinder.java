/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps;

/**
 *
 * @author Adrian
 */
public class PathFinder {

    public static GridNode[] findForcedNeighbors(Grid myGrid, GridNode origin, Vector2 direction) {
        GridNode[] pathNodes = new GridNode[2];
        int numNodes = 0;
        if (direction.x == 0) {
            //Direction is vertical
            if (!myGrid.getNode(origin.x + 1, origin.y).isPassable() && myGrid.getNode(origin.x + 1, origin.y + direction.y).isPassable()) {
                //found forced neighbor to the right and ahead
                pathNodes[numNodes] = myGrid.getNode(origin.x + 1, origin.y + direction.y);
                numNodes++;
            }
            if (!myGrid.getNode(origin.x - 1, origin.y).isPassable() && myGrid.getNode(origin.x - 1, origin.y + direction.y).isPassable()) {
                //found forced neighbor to the left and ahead
                pathNodes[numNodes] = myGrid.getNode(origin.x - 1, origin.y + direction.y);
                numNodes++;
            }
        } else if (direction.y == 0) {
            //Direction is horizontal
            if (!myGrid.getNode(origin.x, origin.y + 1).isPassable() && myGrid.getNode(origin.x + direction.x, origin.y + 1).isPassable()) {
                //Found forced neighbor above and ahead
                pathNodes[numNodes] = myGrid.getNode(origin.x + direction.x, origin.y + 1);
                numNodes++;
            }
            if (!myGrid.getNode(origin.x, origin.y - 1).isPassable() && myGrid.getNode(origin.x + direction.x, origin.y - 1).isPassable()) {
                //Found forced neighbor below and ahead
                pathNodes[numNodes] = myGrid.getNode(origin.x + direction.x, origin.y - 1);
                numNodes++;
            }
        } else {
            //Direction is diagonal
            if (!myGrid.getNode(origin.x - direction.x, origin.y) && myGrid.getNode(origin.x - direction.x, origin.y + direction.y)) {
                //found forced neighbor to side A
                pathNodes[numNodes] = myGrid.getNode(origin.x - direction.x, origin.y + direction.y);
                numNodes++;
            }
            if (!myGrid.getNode(origin.x, origin.y - direction.y) && myGrid.getNode(origin.x + direction.x, origin.y - direction.y)) {
                //found forced neighbor to side B
                pathNodes[numNodes] = myGrid.getNode(origin.x + direction.x, origin.y - direction.y);
                numNodes++;
            }
        }
        return pathNodes;
    }

}
