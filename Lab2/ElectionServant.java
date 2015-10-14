import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.*;
 
public class ElectionServant extends UnicastRemoteObject implements Election {
	private Hashtable<String, Integer> results;
	private ArrayList<Integer> usedVN;
 
    public ElectionServant() throws RemoteException{
        try {
			String ls = lastState();
			ls = ls.replace("{", "");
			ls = ls.replace("}", "");
			String [] tokens = ls.split(",");
			
			results = new Hashtable<String, Integer>();
			usedVN = new ArrayList<Integer>();
			
			for(int i =0;i<tokens.length;i++){
				String [] values = tokens[i].split("=");
				values[0] = values[0].replace(" ","");
				int num = Integer.parseInt(values[1]);
				results.put(values[0],num);
			}
		}
		catch (FileNotFoundException e){
			results = new Hashtable<String, Integer>();
			usedVN = new ArrayList<Integer>();
		}
		catch(IOException e ){
			results = new Hashtable<String, Integer>();
			usedVN = new ArrayList<Integer>();
		}
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
			
			try{
			OutputStream os = new FileOutputStream("state.txt");
			Writer osw = new OutputStreamWriter(os);
			osw.write(results.toString());
			osw.close();	
			}
			catch(FileNotFoundException e){}catch(IOException e){}
				
		}
	}
	
	@Override
	public Hashtable<String, Integer> result() throws RemoteException{
		return results;
	}
	
	public String lastState() throws FileNotFoundException, IOException{
		
		FileInputStream in = new FileInputStream("state.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String state = br.readLine();
		in.close();
		return state;

		
	}
}
