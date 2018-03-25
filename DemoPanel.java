
package lumberlots;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author nick
 */
public class DemoPanel extends JPanel{
    
    private SchoolTile[] schoolTiles = new SchoolTile[6];
    private int[] carCount = new int[8];
    private JLabel[] lotName = new JLabel[6];
    //public JLabel[] carsInLot = new JLabel[8];
    
    Font myBigFont = (new Font("Bookman Old Style", Font.BOLD, 30));
    Font mySmallFont = (new Font("Bookman Old Style", Font.BOLD, 20));
    
    //constructor
    public DemoPanel(){       
        this.setLayout(new GridLayout(3,2));
        nameLots();
        for(int i = 0; i < 6; i++){
            SchoolTile tile = new SchoolTile(i);
            schoolTiles[i] = tile;
            this.add(tile);
            carCount[i+2] = 0; //set 0 cars into each lot
            //carsInLot[i+2] = new JLabel();
            //carsInLot[i+2].setText(Integer.toString(carCount[i+2]));
        }
    }
    
    //contains name, add and remove button, and car count
    private class SchoolTile extends JPanel{
        JLabel carsInLot;
        //JLabel carsInLot = new JLabel();
        SchoolTile(int id){
            this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            this.setLayout(new BorderLayout());
            AddButton add = new AddButton(id);
            RemoveButton remove = new RemoveButton(id);
            this.add(add, BorderLayout.WEST);
            this.add(remove, BorderLayout.EAST);
            
            carsInLot = new JLabel(Integer.toString(carCount[id]));
            
            //carsInLot[id+2].setText("asdf");
            
            carsInLot.setHorizontalAlignment(SwingConstants.CENTER);
            carsInLot.setFont(mySmallFont);
            this.add(carsInLot, BorderLayout.SOUTH);
            this.add(lotName[id], BorderLayout.NORTH);
            this.setPreferredSize(new Dimension(300, 300));

        }
    
    }
    private void nameLots(){
        //lotName[0] = new JLabel("Creek View"); //NUC only
        lotName[0] = new JLabel("Jolly Giant");
        lotName[1] = new JLabel("The one behind the gym");
        lotName[2] = new JLabel("College Creek");
        lotName[3] = new JLabel("BSS");
        lotName[4] = new JLabel("CR A");
        lotName[5] = new JLabel("CR B");
        for(int i = 0; i <=5; i++){
            lotName[i].setHorizontalAlignment(SwingConstants.CENTER);
            lotName[i].setFont(mySmallFont);
        }
    }
    
    private void showCarsInLots(){
        JLabel test = new JLabel("Test");
        schoolTiles[1].add(test, BorderLayout.SOUTH);
    }

    private class AddButton extends JButton{
        int Id;
        AddButton(int id){
            this.setText("Add Car");
            this.addActionListener(new AddCarListener());
            Id = id+2;
        }
        public int getId(){
            return Id;
        }
    }
    
    private class RemoveButton extends JButton{
        int Id;
        RemoveButton(int id){
        this.setText("Remove Car");
        this.addActionListener(new RemoveCarListener());
        Id = id+2;
        }
        public int getId(){
            return Id;
        }
    }
       
    private class AddCarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                AddButton source = (AddButton)ae.getSource();
                int Id = source.getId();
                increaseCarCount(Id);
                schoolTiles[Id-2].carsInLot.setText(Integer.toString(carCount[Id]));
                //carsInLot[Id-1].setText(Integer.toString(carCount[Id-1]));
                
            } catch (Exception ex) {
                Logger.getLogger(DemoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class RemoveCarListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                RemoveButton source = (RemoveButton)ae.getSource();
                int Id = source.getId();
                decreaseCarCount(Id);
            } catch (Exception ex) {
                Logger.getLogger(DemoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        public static void getHTML(String urlToRead) throws Exception {
            URL url = new URL(urlToRead);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            reader.close();
        }
    
        private String generateUrl(int lotNum){
            String url = "https://wizards13467653673838292827.herokuapp.com/lot_update/" 
                + String.valueOf(lotNum) + "/qty/" + String.valueOf(carCount[lotNum]);
            return url;
        }
    
        private void increaseCarCount(int lotNum) throws Exception{
            carCount[lotNum]++;    
            try{
                String url = generateUrl(lotNum);
                getHTML(url);
            } catch(java.io.IOException e){
                System.out.println("Car added to lot " + lotNum);
            } 
        }
        
        private void decreaseCarCount(int lotNum) throws Exception{
            carCount[lotNum]--;    
            try{
                String url = generateUrl(lotNum);
                getHTML(url);
            } catch(java.io.IOException e){
                System.out.println("Removed car from lot " + lotNum);
            } 
        }
}