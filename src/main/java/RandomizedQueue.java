import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sl on 27.06.17.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] elements;
    private Stack<Integer> emptyCells;
    private int last;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        elements = (Item[]) new Object[16];
        emptyCells = new Stack<>();
        last = 0;
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (emptyCells.isEmpty()) {
            if (elements.length == last) {
                resize();
            }
            elements[last] = item;
            last++;
        } else {
            elements[emptyCells.pop()] = item;
        }
        size++;
    }

    private void resize() {
        Item[] biggerArray = (Item[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, biggerArray, 0, elements.length);
        elements = biggerArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomCell = getRandomCell();
        Item temp = elements[randomCell];
        elements[randomCell] = null;
        size--;
        if (randomCell == last - 1) {
            last--;
        } else {
            emptyCells.push(randomCell);
        }
        return temp;
    }

    private int getRandomCell() {
        if (last == 0) {
            return 0;
        }
        int rndCell = StdRandom.uniform(last);
        if (elements[rndCell] == null) {
            rndCell = getRandomCell();
        }
        return rndCell;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[getRandomCell()];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] returned = new int[elements.length];
        private int elementsLeft = size;

        @Override
        public boolean hasNext() {
            return elementsLeft > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[getNextUnreturnedIndex()];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }

        private int getNextUnreturnedIndex() {
            int index = getRandomCell();
            if (returned[index] == 1) {
                getNextUnreturnedIndex();
            }
            returned[index] = 1;
            elementsLeft--;
            return index;
        }
    }

    private class Stack<Item> implements Iterable<Item> {
        private Node<Item> first;     // top of stack
        private int n;                // size of the stack

        // helper linked list class
        private class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty stack.
         */
        public Stack() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this stack is empty.
         *
         * @return true if this stack is empty; false otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this stack.
         *
         * @return the number of items in this stack
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this stack.
         *
         * @param item the item to add
         */
        public void push(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }

        /**
         * Removes and returns the item most recently added to this stack.
         *
         * @return the item most recently added
         * @throws NoSuchElementException if this stack is empty
         */
        public Item pop() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            Item item = first.item;        // save item to return
            first = first.next;            // delete first node
            n--;
            return item;                   // return the saved item
        }


        /**
         * Returns (but does not remove) the item most recently added to this stack.
         *
         * @return the item most recently added to this stack
         * @throws NoSuchElementException if this stack is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            return first.item;
        }

        /**
         * Returns a string representation of this stack.
         *
         * @return the sequence of items in this stack in LIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }


        /**
         * Returns an iterator to this stack that iterates through the items in LIFO order.
         *
         * @return an iterator to this stack that iterates through the items in LIFO order
         */
        public Iterator<Item> iterator() {
            return new ListIterator<Item>(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class ListIterator<Item> implements Iterator<Item> {
            private Node<Item> current;

            public ListIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
