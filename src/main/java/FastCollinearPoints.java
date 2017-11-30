import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Sergiy Levchynskyi.
 */
public class FastCollinearPoints {

    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].equals(pointsCopy[i + 1])) {
                throw new IllegalArgumentException();
            }
        }
        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            ArrayList<Slope> slopes = new ArrayList<>();
            for (int j = 0; j < pointsCopy.length; j++) {
                if (i != j) {
                    slopes.add(new Slope(pointsCopy[i].slopeTo(pointsCopy[j]), pointsCopy[i], pointsCopy[j]));
                }
            }
            Collections.sort(slopes);
            for (int k = 0; k < (slopes.size() - 2); k++) {
                int count = 0;
                int m = k;
                while (m < slopes.size() - 1 && slopes.get(m).slope == slopes.get(m + 1).slope) {
                    count++;
                    m++;
                }
                if (count > 2) {
                    foundSegments.add(new LineSegment(slopes.get(k).a, slopes.get(k + count).b));
                    k = k + count - 1;
                }
            }
        }
        segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }

    // the number of line segments

    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private class Slope implements Comparable<Slope> {
        double slope;
        Point a;
        Point b;

        public Slope(double slope, Point a, Point b) {
            this.slope = slope;
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Slope o) {
            if (this.slope < o.slope) {
                return -1;
            }
            if (this.slope > o.slope) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(19000, 10000),
                new Point(18000, 10000),
                new Point(32000, 10000),
                new Point(21000, 10000),
                new Point(1234, 5678),
                new Point(14000, 10000)
        };
        FastCollinearPoints fast = new FastCollinearPoints(points);
        System.out.println(fast.numberOfSegments());
        Arrays.stream(fast.segments).forEach(System.out::println);
    }
}
