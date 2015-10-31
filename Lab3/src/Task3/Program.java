package Task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
public class Program {
    public static void main(String[] args) throws InterruptedException{

        //Strings and ints for K edit distance. Change here!
        String query = "test";
        String output = "ConsumerTask3.txt";
        int k = 0;

        BlockingQueue<Line> queue = new ArrayBlockingQueue<>(10);
        BlockingQueue<Line> queue2 = new ArrayBlockingQueue<>(10);

        MessageQueue<Line> q1 = new MessageQueue<>(queue);
        MessageQueue<Line> q2 = new MessageQueue<>(queue2);

        LineProducer producer = new LineProducer(q1);
        LineConsumer consumer = new LineConsumer(q1,q2,query,output,k);
        ResultConsumer result = new ResultConsumer(q2,"result.txt");

        new Thread(producer);
        new Thread(consumer);
        new Thread(consumer);

        System.out.println("Producer and Consumer has been started");

        List<Thread> threads = new ArrayList<Thread>();
        threads.add(new Thread(producer));
        threads.add(new Thread(consumer));
        threads.add(new Thread(result));

        long start = System.currentTimeMillis();

        for(Thread t: threads){
            t.start();
        }

        for (Thread t :threads){
            t.join();
        }

        long duration = System.currentTimeMillis() - start;

        System.out.println("Total Duration: " + duration + " ms.");
    }
}
