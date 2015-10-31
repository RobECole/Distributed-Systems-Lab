package Task1;

import Task3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
public class Program {
    public static void main(String[] args) throws InterruptedException{
        //String and Int variable to determine the k distance. change here!
        String query = "test";
        int k = 0;

        BlockingQueue<Line> queue = new ArrayBlockingQueue<>(10);
        MessageQueue<Line> queue1 = new MessageQueue<>(queue);

        LineProducer producer = new LineProducer(queue1);
        LineConsumer consumer = new LineConsumer(queue1,query,"ConsumerTask1.txt",k);

        new Thread(producer);
        new Thread(consumer);

        System.out.println("Producer and Consumer has been started");

        List<Thread> threads = new ArrayList<Thread>();
        threads.add(new Thread(producer));
        threads.add(new Thread(consumer));

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
