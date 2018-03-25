package lumberlots;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class LumberLots { 
  public static void main(String args[]) throws MalformedURLException, IOException
  { 
    MainFrame mainFrame = new MainFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    mainFrame.setVisible(true);

  }
}

class MainFrame extends JFrame{
    private final static int DEFAULT_WIDTH = 900;
    private final static int DEFAULT_HEIGHT = 900;
    
    public MainFrame() throws MalformedURLException, IOException{
            this.setResizable(false);
            this.setTitle("Lumber Lots");
            this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            this.setLayout(new GridLayout(3,2));
            DemoPanel demo = new DemoPanel();
            this.add(demo, BorderLayout.CENTER);
    }
}
