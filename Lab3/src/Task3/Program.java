package Task3;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
public class Program {
    public static int numWorkers = 2;
    public static void main(String[] args) throws InterruptedException{



        int kEditD = 2;
        String query = "test";

        BlockingQueue<Line> q = new ArrayBlockingQueue<>(10);
        BlockingQueue<Line> q2 = new ArrayBlockingQueue<>(10);

        LineProducer producer = new LineProducer( new MessageQueue<>(q));

        ResultConsumer result = new ResultConsumer(new MessageQueue<>(q2),"result.txt", query, kEditD );

        List<Thread> threads = new ArrayList<Thread>();

        for (int i =0; i<numWorkers ; i++){
            threads.add(new Thread(new LineConsumer(new MessageQueue<>(q),new MessageQueue<>(q2))));
        }

        threads.add(new Thread(producer));
        threads.add(new Thread(result));

        System.out.println("Producer and Consumer has been started");
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
