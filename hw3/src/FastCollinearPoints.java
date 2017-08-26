import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * Created by jia on 8/12/17.
 */
public class FastCollinearPoints {
    //private Point[] rest;
    private int elements;
    //private double[] slope;
    private double slopeStart;
    private double slopeEnd;
    private Point[] secCopy;
    private LineSegment[] s;
    private int num;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {



        if (points == null) throw new java.lang.IllegalArgumentException("no argument");

        if (points.length == 0) throw new java.lang.IllegalArgumentException("argument is empty");

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException("argument is null");
        }
        
        Point[] copy = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = points[i];
        }
        //System.arraycopy(points, 0, copy, 0, points.length);

        Arrays.sort(copy);

        for (int i = 1; i < copy.length; i++) {
            if (copy[i].compareTo(copy[i-1]) == 0)
                throw new java.lang.IllegalArgumentException("repeated points");
        }


        elements = 0;
        s  = new LineSegment[0];
        num = 0;
        points = null;


        secCopy = copy.clone();


        for (int i = 0 ; i < copy.length; i++){


            Arrays.sort(secCopy,copy[i].slopeOrder());

            for(int j = 0; j < copy.length; j = j + elements - 1){
                elements = 2;
                slopeStart = copy[i].slopeTo(secCopy[j]);

                int end = j;
                int start = j;
                for (int k = j + 1; k < copy.length; k++) {
                    slopeEnd = copy[i].slopeTo(secCopy[k]);
                    if (slopeStart == slopeEnd) {
                        elements += 1;
                        end = k;
                    } else break;
                }

                if (elements >= 4) {
                    Point max = secCopy[j];
                    Point min = secCopy[j];
                    for (int k = j + 1; k <= end; k++){
                        if (secCopy[k].compareTo(max) == 1) max = secCopy[k];
                        if (secCopy[k].compareTo(min) == -1) min = secCopy[k];

                    }
                    if (copy[i].compareTo(min) == -1) {
                        num += 1;
                        LineSegment[] temp = new LineSegment[num];
                        System.arraycopy(s, 0, temp, 0, s.length);
                        //temp[num - 1] = new LineSegment(copy[i], secCopy[end]);
                        s = temp;
                        s[num - 1] = new LineSegment(copy[i], max);
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments(){
        return num;
    }

    // the line segments
    public LineSegment[] segments(){
        return s;
    }

    public static void main(String[] args) {

        // read the n points from a file

        //In in = new In(args[0]);
        In in = new In("collinear/horizontal5.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
