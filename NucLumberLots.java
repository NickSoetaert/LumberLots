import java.io.*;
import java.net.*;
import java.util.Scanner;

public class NucLumberLots { 
  public static void main(String args[]) throws MalformedURLException, IOException, Exception
  { 
      HsuLot1 lot = new HsuLot1();
      int x = 0;
      while(x < 10){
          lot.getInput();
          x++;
      }
  }
}
    
class HsuLot1{
    private int carCount;

    public HsuLot1(){
        carCount = 0;
    }
        
    private String generateUrl(){
        String url = "https://wizards13467653673838292827.herokuapp.com/lot_update/1/qty/"
                + String.valueOf(carCount);
        return url;
    }
    
    public void getInput() throws Exception{
        Scanner inputReader = new Scanner(System.in);
        int input = inputReader.nextInt();
        
        //car leaves
        if(input == 0){
            carCount--;
        } 
        else {
            carCount++;
        }
            try{
                String url = generateUrl();
                getHTML(url);
            } catch(java.io.IOException e){
                System.out.println("Car moved - " + carCount + " cars in " + "HSU lot 1");
            } 
        }
    
   public void getHTML(String urlToRead) throws Exception {
      URL url = new URL(urlToRead);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      reader.close();
   }
}