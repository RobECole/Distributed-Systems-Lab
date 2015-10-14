import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Hashtable;
 
public class ElectionClient {
    private ElectionClient() {}
        
    public static void main(String[] args) {
        //args[0]: hostname of server where registry resides
        //args[1]: port of registry on that server
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            Election election = (Election) registry.lookup("Election");
            System.out.println("Server Found!");
            
            for(int n=0; n < 10;n++){
               election.vote("Trump",n);
            }
            
            for(int n=11; n < 16;n++){
                election.vote("Mulhair", n);
            }
           Hashtable<String, Integer> results = election.result();
            System.out.println(results);
        } catch(Exception e) {
            System.err.println("ElectionClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
}