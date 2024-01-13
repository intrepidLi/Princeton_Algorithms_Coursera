import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque <Item> implements Iterable<Item> {
    private Node<Item> first; // beginning of deque
    private Node<Item> last; // end of deque
    private int n; // number of elements on queue

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("addFirst element shouldn't be null!!!!");
        }
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldfirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("addLast element shouldn't be null!!!!");
        }
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.prev = null;
            first = last;
        }
        else {
            last.prev = oldlast;
            oldlast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("remove first element failed!!!");
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("remove last element failed!!!");
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Iterator has no next!!!");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            dq.addLast("B" + i);
        }
        for (String s : dq) {
            StdOut.println(s);
        }
        StdOut.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 10; i++) {
            StdOut.println(dq.removeFirst());
            StdOut.println(dq.removeLast());
            StdOut.println(dq.size());
        }
        /* Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        for (Integer s : deque) {
              System.out.println(s);
        }
        StdOut.println(deque.iterator());
        deque.removeLast();
        for (Integer s : deque) {
            System.out.println(s);
        }
        StdOut.println(deque.iterator());*/
    }

}
