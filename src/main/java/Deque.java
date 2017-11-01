import java.util.*;

/**
 * Created by sl on 06.06.17.
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node<Item> first;
    private Node<Item> last;

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;

        public Node() {
        }

        public Node(Item item) {
            this.item = item;
        }
    }

    // implemented in ancestor
    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return size;
    }                        // return the number of items on the deque

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> formerFirst = first;
        Node<Item> newNode = new Node<>(item);
        first = newNode;
        if (size == 0) {
            last = newNode;
        } else {
            formerFirst.prev = newNode;
            newNode.next = formerFirst;
        }
        size++;
    }

/*    private void resize() {
        Item[] temp = (Item[]) new Object[elements.length * 2];
        int newFirst = elements.length - n / 2;
        for (int i = 0; i < elements.length; i++) {
            temp[newFirst + i] = elements[first + i];
        }
        first = newFirst;
        last = newFirst + elements.length;
        elements = temp;
    }*/

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> formerLast = last;
        Node<Item> newNode = new Node<>(item);
        last = newNode;
        if (size == 0) {
            first = newNode;
        } else {
            formerLast.next = newNode;
            newNode.prev = formerLast;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item temp = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        } else {
            first.prev = null;
        }
        size--;
        return temp;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item temp = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        } else {
            last.next = null;
        }
        size--;
        return temp;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> next;

        public DequeIterator() {
            next = first;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item t = next.item;
            next = next.next;
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

/*    public static void main(String[] args) {
        Deque<Double> test = new Deque<>();
        List<Double> input = Arrays.asList(1.2, 0.0, 3.0, 1.5, 2.2);
        for (int i = 0; i < 100; i++) {
            Collections.shuffle(input);
            test.addFirst(input.get(0));
        }
    }*/
}
