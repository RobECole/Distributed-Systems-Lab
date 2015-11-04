package Task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileIterator implements Iterable<Line> {

    final String fileName;
    final int repeat;

    FileIterator(String filename, int repeat) {
        this.fileName = filename;
        this.repeat = repeat;
    }

    @Override
    public Iterator<Line> iterator() {
        return new SubClass();
    }

    class SubClass implements Iterator<Line> {
        public int count;
        // The stream we're reading from
        BufferedReader in;

        // Return value of next call to next()
        String nextline;
        long lineNo = 0;

        public SubClass() {
            count = 1;
            try {
                    in = new BufferedReader(new FileReader(fileName));
                    nextline = in.readLine();
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        // If the next line is non-null, then we have a next line
        @Override
        public boolean hasNext() {
            return nextline != null;
        }

        // Return the next line, but first read the line that follows it.
        @Override
        public Line next() {
                try {
                    String result = nextline;
                    lineNo++;
                    // If we haven't reached EOF yet
                    if (nextline != null) {
                        nextline = in.readLine(); // Read another line

                        if (nextline == null) {
                            if(count<repeat) {
                                lineNo = 0;
                                count++;
                                in.close(); // And close on EOF
                                in = new BufferedReader(new FileReader(fileName));
                                nextline = in.readLine();
                            }
                            else
                            return new Line(result, -1);
                        }
                    }

                    // Return the line we read last time through.
                    return new Line(result,lineNo);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
        }

    }
}