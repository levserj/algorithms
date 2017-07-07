/**
 * Created by sl on 28.06.17.
 */
public class Permutation {
    private static RandomizedQueue<String> elements = new RandomizedQueue<>();

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            elements.enqueue(args[i]);
        }
        for (int j=0; j<3; j++){
            System.out.println(elements.dequeue());
        }
    }
}