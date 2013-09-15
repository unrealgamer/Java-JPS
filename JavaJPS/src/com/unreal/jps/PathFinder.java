/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unreal.jps;

import com.unreal.jps.utility.BinaryHeap;
import com.unreal.jps.utility.PriorityQueue;
import java.util.ArrayList;

/**
 *
 * @author Adrian
 */
public class PathFinder {

    public static void jumpPointSearch(Grid theGrid, int startX, int startY, int endX, int endY) {
        PriorityQueue openList = new BinaryHeap();

        GridNode startNode = theGrid.getNode(startX, startY);
        GridNode endNode = theGrid.getNode(endX, endY);

        openList.add(startNode);

        while (!openList.isEmpty()) {
            GridNode node = (GridNode) openList.pop();

            if (node.equals(endNode)) {
                return;
            }
            //findSuccessors(node, theGrid);

            GridNode parentNode = (GridNode) node.getParent();
            if (parentNode != null) {
                //check direction away from parentnode
                int px = parentNode.getX(), py = parentNode.getY();

                int dx = (node.getX() - px) / Math.abs(node.getX() - px);
                int dy = (node.getY() - py) / Math.abs(node.getY() - py);

                searchRay(theGrid, openList, node, endNode, dx, dy);
            } else {
                //check all diagonals
                searchRay(theGrid, openList, node, endNode, 1, 1);
                searchRay(theGrid, openList, node, endNode, -1, 1);
                searchRay(theGrid, openList, node, endNode, -1, -1);
                searchRay(theGrid, openList, node, endNode, 1, -1);
            }

        }
    }

    private static void searchRay(Grid theGrid, PriorityQueue openList, GridNode origin, GridNode goal, int dx, int dy) {
        int px = origin.getX();
        int py = origin.getY();
        int step = 0;
        int tempX, tempY;
        GridNode tempNode;
        do {
            tempX = px + dx * step;
            tempY = py + dy * step;
            tempNode = theGrid.getNode(tempX, tempY);

            if (goal.getX() == tempX && goal.getY() == tempY) {
                //WOO FOUND THE GOAL BACK THE FUCK OUT OF THERE K
                return;
            }

            if (dx != 0 && dy != 0) {
                //we're checking diagonally :o
                //Search down and horizontal first
                searchRay(theGrid, openList, tempNode, goal, dx, 0);
                searchRay(theGrid, openList, tempNode, goal, 0, dy);
            }

            ArrayList<GridNode> forcedNeighbors = findForcedNeighbors(theGrid, tempNode, new Vector2(dx, dy));
            if (forcedNeighbors.size() > 0) {
                tempNode.setParent(origin);
                openList.add(tempNode);
                for (GridNode node : forcedNeighbors) {
                    node.setParent(tempNode);
                    openList.add(node);
                }
            }
        } while (theGrid.getNode(tempX + dx, tempY + dy).isPassable());
    }

    public static ArrayList<GridNode> findForcedNeighbors(Grid myGrid, GridNode origin, Vector2 direction) {
        ArrayList<GridNode> pathNodes = new ArrayList();
        if (direction.x == 0) {
            //Direction is vertical
            if (!myGrid.getNode(origin.getX() + 1, origin.getY()).isPassable() && myGrid.getNode(origin.getX() + 1, origin.getY() + direction.y).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() + 1, origin.getY() + direction.y));
            }
            if (!myGrid.getNode(origin.getX() - 1, origin.getY()).isPassable() && myGrid.getNode(origin.getX() - 1, origin.getY() + direction.y).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() - 1, origin.getY() + direction.y));
            }
        } else if (direction.y == 0) {
            //Direction is horizontal
            if (!myGrid.getNode(origin.getX(), origin.getY() + 1).isPassable() && myGrid.getNode(origin.getX() + direction.x, origin.getY() + 1).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() + direction.x, origin.getY() + 1));
            }
            if (!myGrid.getNode(origin.getX(), origin.getY() - 1).isPassable() && myGrid.getNode(origin.getX() + direction.x, origin.getY() - 1).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() + direction.x, origin.getY() - 1));
            }
        } else {
            //Direction is diagonal
            if (!myGrid.getNode(origin.getX() - direction.x, origin.getY()).isPassable() && myGrid.getNode(origin.getX() - direction.x, origin.getY() + direction.y).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() - direction.x, origin.getY() + direction.y));
            }
            if (!myGrid.getNode(origin.getX(), origin.getY() - direction.y).isPassable() && myGrid.getNode(origin.getX() + direction.x, origin.getY() - direction.y).isPassable()) {
                pathNodes.add(myGrid.getNode(origin.getX() + direction.x, origin.getY() - direction.y));
            }
        }
        return pathNodes;
    }

}
