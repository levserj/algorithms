import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Sergiy Levchynskyi.
 */
public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;
    private int segmentIndex = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[i + 1])) {
                throw new IllegalArgumentException();
            }
        }
        this.points = points;
        this.segments = new LineSegment[points.length];

        LinkedList<LineSegment> found = new LinkedList<>();
        for (int i = 0; i < points.length; i++) {
/*            Point[] temp = new Point[points.length];*/
            for (int j = i + 1; j < points.length; j++) {
/*                if (points[i].slopeTo(points[j]))*/
                for (int k = j + 1; k < points.length; k++) {
                    inner:
                    for (int m = k + 1; m < points.length; m++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])) {
                            LineSegment newSegment = new LineSegment(points[i], points[m]);
                            for (LineSegment segment : segments) {
                                if (newSegment.equals(segment)) {
                                    continue inner;
                                }
                            }
                            segments[segmentIndex] = newSegment;
                            segmentIndex++;
                        }
                    }
                }
            }
        }
        segments = Arrays.stream(segments).filter(Objects::nonNull).toArray(LineSegment[]::new);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
        BruteCollinearPoints brute = new BruteCollinearPoints(points);
        System.out.println(brute.numberOfSegments());
        Arrays.stream(brute.segments).forEach(System.out::println);
    }
}
