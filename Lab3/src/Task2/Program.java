package Task2;

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

        //Strings and ints for K edit distance. Change here!
        String query = "test";
        String output = "ConsumerTask2.txt";
        int k = 0;


        BlockingQueue<Line> q = new ArrayBlockingQueue<>(10);
        BlockingQueue<Line> q2 = new ArrayBlockingQueue<>(10);

        MessageQueue<Line> queue = new MessageQueue<>(q);
        MessageQueue<Line> queue2 = new MessageQueue<>(q2);

        LineProducer producer = new LineProducer(queue);
        LineConsumer consumer = new LineConsumer(queue,queue2,query,output,k);
        ResultConsumer result = new ResultConsumer(queue2,"result.txt");

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
