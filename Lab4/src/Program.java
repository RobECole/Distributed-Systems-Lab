import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Program{
    public static int numNodes = 8;

    public static void main(String args[]) throws InterruptedException {


        List<Thread> threads = new ArrayList<Thread>();
        for(int i = 1; i<8; i++){
            threads.add(new Thread(new Node((i),((i+1)))));
        }

        threads.add(new Thread(new Node(8,1)));

        Message message = new Message(1, -2, -1);

        //start nodes
        System.out.println("Started");
        for(Thread t: threads){
            t.start();
        }

        //initiate the election
        Socket s = null;
        try{
            int serverPort = 5001;
            String data = Util.toString(message);
            s = new Socket("localhost", serverPort);
            DataOutputStream out = new DataOutputStream( s.getOutputStream());
            out.writeUTF(data);
        }catch (Exception e){
        }finally {
            if(s!=null){
                try {s.close();}
                catch (IOException e){}
            }
        }

        for(Thread t:threads){
            t.join();
        }
        System.out.println("Done");

    }
}
