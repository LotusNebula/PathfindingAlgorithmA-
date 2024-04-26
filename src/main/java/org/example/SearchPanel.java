package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchPanel extends JPanel {
    //Panel settings for GUI
    final int maximumCol = 150;
    final int maximumRow = 150;
    final int nodeSize = 10;

    //Determines the size of the screen based on the grid - this will be
    //changed for final implementation
    final int screenWidth = nodeSize* maximumCol;
    final int screenHeight = nodeSize* maximumRow;

    //Creates the 2-Dimensional array of nodes based off the amount of
    //grid columns and rows
    Node[][] node = new Node[maximumCol][maximumRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openNodeList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    //Other variables for the algorithm
    boolean goalNodeFound = false;
    int step = 0;


    public SearchPanel(){

        //Sets up the JFrame window and visually customises it
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        //Creates a layout to align all of the buttons (nodes)
        this.setLayout(new GridLayout(maximumRow, maximumCol));

        //Allows the buttons to be clicked and toggled
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        //Places the nodes systematically on the screen
        int col = 0;
        int row = 0;

        while(col < maximumCol && row < maximumRow){

            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if(col == maximumCol){
                col = 0;
                row++;
            }
        }

        //Calls the method to set the start and goal nodes
        setStartNode(1,1);
        //setGoalNode(8,5);
        setGoalNode(120,126);


        //Calls the method to place some obstacle nodes
        setObstacleNode(8,2);
        setObstacleNode(9,2);
        setObstacleNode(10,2);
        setObstacleNode(11,2);

        setObstacleNode(6,5);
        setObstacleNode(6,4);
        setObstacleNode(6,3);

        setObstacleNode(1,3);
        setObstacleNode(2,3);
        setObstacleNode(3,3);

        setObstacleNode(120,120);
        setObstacleNode(110,126);

    }
    private void setStartNode(int col, int row) {
        node [col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }
    private void setGoalNode(int col, int row) {
        node [col][row].setAsGoal();
        goalNode = node[col][row];
    }
    private void setObstacleNode(int col, int row) {
        node[col][row].setAsSolid();
    }

    public void search(){

        //While the goal hasn't been found and within a number of steps run the search
        while (goalNodeFound == false && step < 300) {
            int col = currentNode.col;
            int row = currentNode.row;

            //Marks the current node as checked
            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openNodeList.remove(currentNode);

            //Opens the node above
            if (row - 1 >= 0) {
                //Checks that the node exists first
                openNode(node[col][row - 1]);
            }
            //Opens the node to the left
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            //Opens the node below
            if (row + 1 < maximumRow) {
                openNode(node[col][row + 1]);
            }
            //Opens the node above
            if (col + 1 < maximumCol) {
                openNode(node[col + 1][row]);
            }

            //Calculates the best node of the ones surrounding
            int bestNodeIndex = 0;
            int lowestNodeCost = 999;

            for (int i = 0; i < openNodeList.size(); i++){

                //Checks if the nodes F cost is better
                if (openNodeList.get(i).fCost < lowestNodeCost) {
                    bestNodeIndex = i;
                    lowestNodeCost = openNodeList.get(i).fCost;
                }
                //If they are both equal it then checks the G cost
                else if (openNodeList.get(i).fCost == lowestNodeCost){
                    if (openNodeList.get(i).gCost < openNodeList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            //Then moves on to that node and begins the loop again until the goal is found
            currentNode = openNodeList.get(bestNodeIndex);
            if (currentNode == goalNode){
                goalNodeFound = true;
                trackShortestPath();
            }
        }
        step++;
    }
    private void openNode(Node node){
        if (!node.open && !node.checked && !node.solid){

            //If the node is not opened yet, add it to the open list
            node.setAsOpen();
            node.parent = currentNode;
            openNodeList.add(node);
        }
    }

    private void trackShortestPath() {

        //Backtrack and draw the best path
        Node current = goalNode;

        while(current != startNode){
            current = current.parent;

            if(current != startNode) {
                current.setAsPath();
            }
        }
    }

}
