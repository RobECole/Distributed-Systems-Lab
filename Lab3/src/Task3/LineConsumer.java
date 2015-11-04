package Task3;

import java.util.Objects;

/**
 * Created by Robert on 28-Oct-2015.
 */
class LineConsumer implements Runnable {

    private MessageQueue<Line> inputQueue;
    private MessageQueue<Line> outputQueue;



    public LineConsumer(MessageQueue<Line> q1, MessageQueue<Line> q2) {
        this.inputQueue = q1;
        this.outputQueue = q2;
    }


    @Override
    public void run() {
        try {
            Line ln;
            while (!Objects.equals((ln = inputQueue.take()).content, "eof")) {
                Thread.sleep(10);
                System.out.println("Consumed :" + ln.content);
                outputQueue.put(ln);
            }
            outputQueue.put(new Line("eof", -1));

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}