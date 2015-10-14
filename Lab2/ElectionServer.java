import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
 
public class ElectionServer {

    public static void main(String args[]) {
        try {
            // args[0]: hostname
            String host = args[0];
            System.setProperty("java.rmi.server.hostname", host);
            ElectionServant obj = new ElectionServant();
 
            // Bind the remote object's stub in the registry
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry();
                registry.list();
            }catch (RemoteException e) {
                System.out.println("RMI registry cannot be located at port " + Registry.REGISTRY_PORT);
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                System.out.println("RMI registry created at port " + Registry.REGISTRY_PORT);
            }
             
            registry.rebind("Election", obj);
            System.out.println("ElectionServer ready");
        }catch(Exception e) {
            System.err.println("ElectionServer exception: " + e.toString());
            e.printStackTrace();
        }
 
    }
}