package Task3;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
public class MessageQueue<T> {

    private BlockingQueue<Line> Q;

    public MessageQueue(BlockingQueue<Line> bq){
        this.Q = bq;
    }

    public void put(Line ln) throws InterruptedException {
        if (!(ln.content.contains("Distributed Systems"))){
            Q.put(ln);
        }
    }

    public Line take() throws InterruptedException {
        return Q.take();
    }
}
