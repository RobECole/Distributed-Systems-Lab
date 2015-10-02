import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TCPClient {
    
    public static void main (String args[]) {
        // arguments supply message and hostname of destination
        Socket s = null;
        Scanner input = new Scanner(System.in);
        int messageSize = Integer.parseInt(args[1]);
        int messageCount = Integer.parseInt(args[2]);
        String message = "";
        for (int j=0; j<messageSize; j++){
            message = message + "A";
        }
        try{
            int serverPort = 7896;
            s = new Socket(args[0], serverPort);
            s.setSoTimeout(5000);    
 
            for(int j=0; j<messageCount ; j++){
                DataInputStream in = new DataInputStream( s.getInputStream());
                DataOutputStream out = new DataOutputStream( s.getOutputStream());
                String dataPacket = j + "|" + message;
                out.writeUTF(dataPacket);
         // Try to read incoming message
              String data = in.readUTF();
              System.out.println("Received: "+ data); 
           }
        }
        catch (SocketTimeoutException ste){
            System.out.println("STE:" + ste.getMessage());
        }catch (UnknownHostException e){
            System.out.println("Sock:"+e.getMessage()); 
        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){
            System.out.println("IO:"+e.getMessage());
        }
        finally {if(s!=null) try {s.close();}
    catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
}
