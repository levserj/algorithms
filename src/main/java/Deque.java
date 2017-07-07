import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sl on 06.06.17.
 */
public class Deque<Item> implements Iterable<Item> {

    private Item[] elements;
    private int size;
    private Node<Item> first;
    private Node<Item> last;

    // construct an empty deque
    public Deque() {
        elements = (Item[]) new Object[16];
        size = 0;
        first = null;
        last = null;
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // implemented in ancestor
    public boolean isEmpty() {
        return size == 0;
    }                 // is the deque empty?

    public int size() {
        return size();
    }                        // return the number of items on the deque

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node<Item>();
        newNode.item = item;
        if (first == null) {
             first = last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
            newNode.prev = last;
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
        Node newNode = new Node<Item>();
        newNode.item = item;
        if (last == null) {
            last = first = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item temp = first.item;
        first = first.next;
        size--;
        return temp;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item temp = last.item;
        last = last.prev;
        size--;
        return temp;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current;

        public DequeIterator(){
            current = first;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item t = current.item;
            current = current.next;
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
