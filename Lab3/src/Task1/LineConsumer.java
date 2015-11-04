package Task1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * Created by Robert on 28-Oct-2015.
 */
class LineConsumer implements Runnable {

    private MessageQueue<Line> bq;
    private String word, output;
    private int k;

    public LineConsumer(MessageQueue<Line> q1,String query, String output, int k)
          {
              this.bq = q1;
              this.word = query;
              this.k = k;
              this.output = output;
          }

    @Override
    public void run() {
        try{
            Line ln;
            PrintWriter writer = new PrintWriter(output, "UTF-8");

            while(!Objects.equals((ln = bq.take()).content, "eof")){
                System.out.println("Consumed "+ ln.content);
                String[] tokenLn = Util.words(ln.content);
                for (String str: tokenLn){
                    int kEdit = Util.editDistance(str,word);
                    if ( kEdit <= k){
                        writer.println(ln.lineNumber + ":" + ln.content);
                    }
                }
            }

            writer.close();
        }catch (InterruptedException | FileNotFoundException | UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}