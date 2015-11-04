package Task2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Robert on 28-Oct-2015.
 */
class ResultConsumer implements Runnable {

    private MessageQueue<Line> bq;
    private String output,check;
    private int num;

    public ResultConsumer(MessageQueue<Line> q1, String o, String w,int k)
    {
        this.bq = q1;
        this.output = o;
        this.check = w;
        this.num = k;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(10);
            Line ln;
            PrintWriter writer = new PrintWriter(output, "UTF-8");

            while(!Objects.equals((ln = bq.take()).content, "eof")){
                String[] tokenLn = Util.words(ln.content);
                for (String str : tokenLn) {
                    int kEdit = Util.editDistance(str, check);
                    if (kEdit <= num) {
                        writer.println(ln.lineNumber + ":" + ln.content);
                    }
                }

            }
            writer.close();
        }catch (InterruptedException | UnsupportedEncodingException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
}