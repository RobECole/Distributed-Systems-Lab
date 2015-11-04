package Task2;


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
        FileIterator fi = new FileIterator(fileName,10);
        for(Line ln : fi) {
            try {
                Thread.sleep(10);
                bq.put(ln);
                System.out.println("Produced :" + ln.content);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //adding exit message
        for (int i = 0; i<Program.numWorkers; i++){
            Line ln = new Line("eof", -1);
            try {
                bq.put(ln);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

