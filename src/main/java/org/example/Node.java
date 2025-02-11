package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton implements ActionListener {

    Node parent;
    int col;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row){
        this.col = col;
        this.row = row;

        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

    }
    public void setAsStart(){
        setBackground(Color.MAGENTA);
        setForeground(Color.WHITE);
        setText("Start");
        start = true;
    }

    public void setAsGoal(){
        setBackground(Color.CYAN);
        setForeground(Color.BLACK);
        setText("Goal");
        goal = true;
    }

    public void setAsSolid(){
        setBackground(Color.BLACK);
        setForeground(Color.BLACK);
        solid =  true;
    }

    public void setAsOpen(){
        open = true;
    }
    public void setAsChecked(){
        if(start == false && goal == false){
            setBackground(Color.ORANGE);
            setForeground(Color.BLACK);
        }
        checked = true;
    }
    public void setAsPath(){
        setBackground(Color.GREEN);
        setForeground(Color.BLACK);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        setBackground(Color.ORANGE);
    }
}
