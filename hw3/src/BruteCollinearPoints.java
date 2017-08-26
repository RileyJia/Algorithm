import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by jia on 8/12/17.
 */
public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private int num;
    private LineSegment[] s;

    public BruteCollinearPoints(Point[] points) {

        Point[] copy = new Point[points.length];
        System.arraycopy(points, 0, copy, 0, points.length);


        Arrays.sort(copy);

        if (points == null) throw new java.lang.IllegalArgumentException("no argument");

        if (points.length == 0) throw new java.lang.IllegalArgumentException("argument is empty");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("argument is null");
        }


        for (int i = 1; i < points.length; i++) {
            if (copy[i].compareTo(copy[i-1]) == 0)
                throw new java.lang.IllegalArgumentException("repeated points");
        }

        points = null;

        num = 0;
        s  = new LineSegment[0];

        for (int i = 0; i < copy.length; i++) {
            for (int j = i + 1; j < copy.length; j++) {
                for (int k = j + 1; k < copy.length; k++) {
                    if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[k])) {

                        for (int q = k + 1; q < copy.length; q++) {
                            if (copy[i].slopeTo(copy[j]) == copy[i].slopeTo(copy[q])) {
                                num += 1;
                                LineSegment[] temp = new LineSegment[num];
                                System.arraycopy(s, 0, temp, 0, s.length);
                                s = temp;
                                s[num - 1] = new LineSegment(copy[i], copy[q]);
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return num;
    }

    // the line segments
    public LineSegment[] segments() {
        return s;
    }

    public static void main(String[] args) {

        // read the n points from a file
        // In in = new In(args[0]);
        In in = new In("input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
