package Task2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Objects;


/**
 * Created by Robert on 28-Oct-2015.
 */
class LineConsumer implements Runnable {

    private MessageQueue<Line> bq;
    private MessageQueue<Line> pq;
    private String output;
    private String query;
    private int k;


    public LineConsumer(MessageQueue<Line> q1, MessageQueue<Line> q2, String q, String o, int k) {
        this.bq = q1;
        this.pq = q2;
        this.output = o;
        this.query = q;
        this.k = k;
    }


    @Override
    public void run() {
        try {
            Line ln;
            PrintWriter writer = new PrintWriter(output, "UTF-8");

            while (!Objects.equals((ln = bq.take()).content, "eof")) {
                Thread.sleep(10);
                System.out.println("Consumed " + ln.content);
                String[] tokenLn = Util.words(ln.content);
                for (String str : tokenLn) {
                    int kEdit = Util.editDistance(str, query);
                    if (kEdit <= k) {
                        writer.println(ln.lineNumber + ":" + ln.content);
                    }
                }
                pq.put(new Line("eof", -1));

            }
            writer.close();
        } catch (InterruptedException | FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}