import java.net.*;
import java.io.*;
 
public class UDPClient{
    public static void main(String args[]){ 
    // args give message contents and server hostname
    DatagramSocket aSocket = null;
    String name = args[0];
    int messageSize = Integer.parseInt(args[1]);
    int messageCount = Integer.parseInt(args[2]);
    String message = "";
      for (int j=0; j<messageSize; j++){
          message = message + "A";
      }
      try {
          for(int i = 0; i < messageCount; i++){
            aSocket = new DatagramSocket();
            aSocket.setSoTimeout(500);
            
            String packedData = i + "|" + message + "|" + messageCount;
            byte [] m = packedData.getBytes();
            
            InetAddress aHost = InetAddress.getByName(name);
            int serverPort = 6789;                                                       
            DatagramPacket request = new DatagramPacket(m, message.length(), aHost, serverPort);
            aSocket.send(request);
            
                                              
            byte[] buffer = new byte[100];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            try {
                aSocket.receive(reply);
            }catch (SocketTimeoutException e){
                aSocket.close();
            }   
           
            System.out.println("Reply: " + new String(reply.getData()));
            
          }
               
      }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
      }catch (IOException e){
          System.out.println("IO: " + e.getMessage());
      } finally {
          if(aSocket != null) aSocket.close();
        }
   } 
}
