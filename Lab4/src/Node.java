
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Node extends Thread implements Runnable {

    private int nodeId;
    private int neighbourId;
    private int master;
    private boolean election;
    private boolean lap;

    public Node(int id, int neighbourId) {
        this.nodeId = id;
        this.neighbourId = neighbourId;
        election = false;
        master = -1;
    }

    public void setMaster(int master) {
        this.master = master;
    }

    public void onReceiveMessage(Message message) throws IOException {

        //type -1 is election
        if (message.getType() == -1) {
            election = true;
            int biggestPort = message.getVote();

            if (master != biggestPort) {
                if (biggestPort > master && nodeId<biggestPort) {
                    this.setMaster(biggestPort);
                }
                else {
                    this.setMaster(nodeId);
                }
                //send message where id is vote
                Message forwardMessage = new Message(nodeId, master, -1);
                sendMessage(forwardMessage);
            } else {
                Message forwardMessage = new Message(nodeId, master, 1);
                election = false;
                System.out.println("Node: " + nodeId + " says Master is :" + master);
                sendMessage(forwardMessage);
            }
        }
        //type 1 is master found
        else if (message.getType() == 1) {
            election = false;
            master = message.getVote();
            if (nodeId != master && !lap) {
                lap = true;
                Message forwardMessage = new Message(nodeId, master, 1);
                System.out.println("Node: " + nodeId + " says Master is :" + master);
                sendMessage(forwardMessage);
            }
            throw new EOFException();
        } else {
            System.out.println("Node: " + nodeId + "sitting on message: " + message.toString());
        }

    }

    //send message to neighbouring node
    public void sendMessage(Message message) throws IOException {
        Socket s = null;
        try {
            int serverPort = 5000 + this.neighbourId;
            String data = Util.toString(message);
            s = new Socket("localhost", serverPort);
            System.out.println(nodeId + " Sending: " + neighbourId + " Message: " + message.toString());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Override
    public void run() {
        try {

            int serverPort = 5000 + nodeId;

            ServerSocket listenSocket = new ServerSocket(serverPort);

            //loop to keep listening for messages until leader is found
            while (true) {
                try {
                    Socket nodeSocket = listenSocket.accept();
                    DataInputStream in = new DataInputStream(nodeSocket.getInputStream());
                    String data = in.readUTF();
                    Message m = (Message) Util.fromString(data);
                    System.out.println("Node: " + nodeId + " Received Message :" + m.toString());
                    onReceiveMessage(m);
                } catch (EOFException e) {
                    listenSocket.close();
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
