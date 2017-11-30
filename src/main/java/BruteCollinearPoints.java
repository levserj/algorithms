import java.util.Arrays;
import java.util.Objects;

/**
 * @author Sergiy Levchynskyi.
 */
public class BruteCollinearPoints {

    private LineSegment[] segments;

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
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(copyPoints);
        for (int i = 0; i < copyPoints.length - 1; i++) {
            if (copyPoints[i].equals(copyPoints[i + 1])) {
                throw new IllegalArgumentException();
            }
        }
        this.segments = new LineSegment[copyPoints.length];

        int segmentIndex = 0;
        for (int i = 0; i < copyPoints.length; i++) {
            for (int j = i + 1; j < copyPoints.length; j++) {
                for (int k = j + 1; k < copyPoints.length; k++) {
                    inner:
                    for (int m = k + 1; m < copyPoints.length; m++) {
                        if (copyPoints[i].slopeTo(copyPoints[j]) == copyPoints[i].slopeTo(copyPoints[k]) &&
                                copyPoints[i].slopeTo(copyPoints[j]) == copyPoints[i].slopeTo(copyPoints[m])) {
                            LineSegment newSegment = new LineSegment(copyPoints[i], copyPoints[m]);
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
        return Arrays.copyOf(segments, segments.length);
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
