import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
 
public interface Election  extends Remote {
    void vote(String c, int vn) throws RemoteException;
    Hashtable<String, Integer> result() throws RemoteException;
}