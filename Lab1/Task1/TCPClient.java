import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TCPClient {
    public static void main (String args[]) {
        // arguments supply message and hostname of destination
        Socket s = null;
        Scanner input = new Scanner(System.in);
        try{
            int serverPort = 7896;
            s = new Socket(args[0], serverPort);    
            DataInputStream in = new DataInputStream( s.getInputStream());
            DataOutputStream out =
                new DataOutputStream( s.getOutputStream());
            while(true){
                String message;
                System.out.println("Enter Message:");
                message = input.nextLine();
                out.writeUTF(message);          // UTF is a string encoding see Sn 4.3
                String data = in.readUTF();       
                System.out.println("Received: "+ data) ;      
            }
    
        }catch (UnknownHostException e){
            System.out.println("Sock:"+e.getMessage()); 
        }catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        }catch (IOException e){
            System.out.println("IO:"+e.getMessage());
        }finally {if(s!=null) try {s.close();}
    catch (IOException e){System.out.println("close:"+e.getMessage());}}
    }
}
