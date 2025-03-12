package com.project.view;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    int WIDTH = 1200;
    int HEIGHT = 800;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int)screenSize.getWidth()/2-(WIDTH/2);
    int screenHeight = (int)screenSize.getHeight()/2-(HEIGHT/2);

    Panel panel = new Panel(WIDTH,HEIGHT);

    public Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("EKOMOBİL 2");
        this.setResizable(false);
        this.setLocation(screenWidth,screenHeight);
        this.setSize(WIDTH,HEIGHT);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
