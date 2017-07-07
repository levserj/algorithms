import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sl on 06.06.17.
 */
public class DequeArrays<Item> implements Iterable<Item> {

    private Item[] elements;
    private int n;
    private int first;
    private int last;

    // construct an empty deque
    public DequeArrays() {
        elements = (Item[]) new Object[16];
        n = 0;
        first = 8;
        last = 8;
    }

    // implemented in ancestor
    public boolean isEmpty() {
        return n == 0;
    }                 // is the deque empty?

    public int size() {
        return n;
    }                        // return the number of items on the deque

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if ((first - 1) == 0) {
            resize();
        }
        elements[first - 1] = item;
        first = first - 1;
        n++;
    }          // add the item to the front

    private void resize() {
        Item[] temp = (Item[]) new Object[elements.length * 2];
        int newFirst = elements.length - n / 2;
        for (int i = 0; i < elements.length; i++) {
            temp[newFirst + i] = elements[first + i];
        }
        first = newFirst;
        last = newFirst + elements.length;
        elements = temp;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if ((last + 1) == elements.length) {
            resize();
        }
        elements[last + 1] = item;
        last = last + 1;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("DequeArrays is empty");
        Item temp = elements[first];
        elements[first] = null;
        first = first + 1;
        n--;
        return temp;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("DequeArrays is empty");
        Item temp = elements[last];
        elements[last] = null;
        last = last - 1;
        n--;
        return temp;
    }

    ;                // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }        // return an iterator over items in order from front to end

    private class DequeIterator implements Iterator<Item> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < n;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item t = elements[first + i];
            i++;
            return t;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
