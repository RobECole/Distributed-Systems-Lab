package Task2;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */

class LineProducer implements Runnable {

    private MessageQueue<Line> bq;

    public LineProducer(MessageQueue<Line> q1)
    {
    this.bq=q1;
    }
    /**
     * puts strings from q1
     */
    @Override
    public void run()
    {
        String fileName = "Data.txt";
        //produce message
        FileIterator fi = new FileIterator(fileName);
        for(Line ln : fi) {
            try {
                Thread.sleep(100);
                bq.put(ln);
                System.out.println("Produced :" + ln.content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //adding exit message
        Line ln = new Line("eof", -1);
        try {
            bq.put(ln);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

