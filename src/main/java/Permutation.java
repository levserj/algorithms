import edu.princeton.cs.algs4.StdIn;

/**
 * Created by sl on 28.06.17.
 */
public class Permutation {
    private static RandomizedQueue<String> elements = new RandomizedQueue<>();

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            elements.enqueue(StdIn.readString());
        }
        for (int j = 0; j < k; j++) {
            System.out.println(elements.dequeue());
        }
    }
}