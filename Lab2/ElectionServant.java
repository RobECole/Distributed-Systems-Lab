import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.ArrayList;
 
public class ElectionServant extends UnicastRemoteObject implements Election {
	private Hashtable<String, Integer> results;
	private ArrayList<Integer> usedVN;
 
    public ElectionServant() throws RemoteException{
        results = new Hashtable<String, Integer>();
		usedVN = new ArrayList<Integer>();
    }
 	
	 @Override
	public void vote(String c, int vn) throws RemoteException{
		
		if(!usedVN.contains(vn)){
			if (results.containsKey(c)){
				int num = results.get(c);
				num++;
				results.put(c, num);
				System.out.println("Inc");
			}
			else{
				results.put(c, 1);
				System.out.println("overide");
			}
			usedVN.add(vn);	
			System.out.println("Successfully Votes!");		
		}
	}
	
	@Override
	public Hashtable<String, Integer> result() throws RemoteException{
		return results;
	}
	
}
