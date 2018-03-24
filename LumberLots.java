package lumberlots;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lumberlots.HtmlRequest.getHTML;



public class LumberLots { 
  public static void main(String args[]) throws MalformedURLException, IOException
  { 
    MainFrame mainFrame = new MainFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
    mainFrame.setVisible(true);

  }
}

//Contains the game board, and any additional info such as player name, etc.
class MainFrame extends JFrame{
    private final static int DEFAULT_WIDTH = 800;
    private final static int DEFAULT_HEIGHT = 800;
    
    public MainFrame() throws MalformedURLException, IOException{
            this.setResizable(false);
            this.setTitle("Lumber Lots");
            this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
            this.setLayout(new BorderLayout());
            DemoPanel demo = new DemoPanel();
            this.add(demo, BorderLayout.CENTER);
    }
}
    
class DemoPanel extends JPanel{
    private int carCount = 0;
    private int lotNum = 1;
    private String school = "HSU";
    
    
    String[] schools = {"HSU", "CR"};
    String[] lots = {"1", "2", "3", "4"};
    JComboBox schoolsBox = new JComboBox(schools);
    JComboBox lotBox = new JComboBox(lots);
    JButton addCar = new JButton("Add Car");
    JButton removeCar = new JButton("RemoveCar");
    JLabel carsInLot = new JLabel();
    Font myBigFont = (new Font("Bookman Old Style", Font.BOLD, 30));
    Font mySmallFont = (new Font("Bookman Old Style", Font.BOLD, 20));
    
    public DemoPanel(){
        
        schoolsBox.setFont(mySmallFont);
        lotBox.setFont(mySmallFont);
        
        carsInLot.setHorizontalAlignment(SwingConstants.CENTER);
        carsInLot.setFont(myBigFont);
        this.setLayout(new BorderLayout());
        
        lotBox.addActionListener(new SelectLotListener());
        
        
        addCar.addActionListener(new AddCarListener());
        removeCar.addActionListener(new RemoveCarListener());
        
        carsInLot.setText("Cars In Lot: " + String.valueOf(carCount));
        this.add(carsInLot,BorderLayout.CENTER);
        this.add(addCar, BorderLayout.WEST);
        this.add(removeCar, BorderLayout.EAST);
        this.add(schoolsBox, BorderLayout.NORTH);
        this.add(lotBox, BorderLayout.SOUTH);
        
    }    
    class AddCarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                increaseCarCount();
            } catch (Exception ex) {
                Logger.getLogger(DemoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateDisplay();
        }
    }
        
    class RemoveCarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                decreaseCarCount();
            } catch (Exception ex) {
                Logger.getLogger(DemoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateDisplay();
        }
    }
    
    class SelectSchoolListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            school = (String)ae.getSource();
        }
    }
    
    class SelectLotListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            JComboBox cb = (JComboBox)ae.getSource();
            String tempLotNum = (String)cb.getSelectedItem();
            
            lotNum = Integer.parseInt(tempLotNum);
            
        }
    }
    
    private String generateUrl(){
        String url = "https://wizards13467653673838292827.herokuapp.com/lot_update/" 
                + String.valueOf(lotNum) + "/qty/" + String.valueOf(carCount);
        return url;
    }

    
    private void increaseCarCount() throws Exception{
        carCount++;    
        try{
            String url = generateUrl();
            getHTML(url);
        } catch(java.io.IOException e){
            System.out.println("Car added to lot" + lotNum);
        } 
    }
    
    private void decreaseCarCount() throws Exception{
        carCount--;
        try{
            String url = generateUrl();
            getHTML(url);
        } catch(java.io.IOException e){
            System.out.println("Car removed from lot" + lotNum);
        } 
    }
    
    private void updateDisplay(){
        carsInLot.setText("Cars In Lot: " + String.valueOf(carCount));
    }
    
}


class HtmlRequest {
   public static void getHTML(String urlToRead) throws Exception {
      URL url = new URL(urlToRead);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      reader.close();
   }
}