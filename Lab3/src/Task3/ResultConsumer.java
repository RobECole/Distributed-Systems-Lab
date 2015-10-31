package Task3;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
class ResultConsumer implements Runnable {

    private MessageQueue<Line> bq;
    private String output;

    public ResultConsumer(MessageQueue<Line> q1, String o)
    {
        this.bq = q1;
        this.output = o;
    }

    @Override
    public void run() {
        try{
            Line ln;
            PrintWriter writer = new PrintWriter(output, "UTF-8");
            while((ln = bq.take()).content != "eof"){
                Thread.sleep(10);
                writer.println(ln.lineNumber + ":" + ln.content);
            }
            writer.close();
        }catch (InterruptedException | UnsupportedEncodingException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
}