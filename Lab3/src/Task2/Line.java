package Task2;

/**
 * Created by Robert on 28-Oct-2015.
 */
class Line {
    String content;
    long lineNumber;

    public Line(String content, long lineNumber) {
        this.content = content;
        this.lineNumber = lineNumber;
    }

        boolean isEnd() {
        return lineNumber < 0;
    }
}