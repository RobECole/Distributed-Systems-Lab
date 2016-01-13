
import java.io.Serializable;

public class Message implements Serializable{

    private final static long serialVersionUID = 1;
    private int type;
    private int sender;
    private int vote;

    public Message(int from, int content, int type){
        this.type = type;
        this.sender = from;
        this.vote = content;
    }

    public int getVote(){
        return vote;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return sender + ":" + vote + ":" + type;
    }
}
