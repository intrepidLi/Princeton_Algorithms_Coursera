import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        // assert capacity >= size;

        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Enqueue argument should not be null!!!");
        }
        if (size == items.length) {
            resize(2 * items.length);
        } // double size of array if necessary
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Dequeue need queue not empty!!!");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;
        size--;

        if (size > 0 && (size == items.length / 4)) {
            resize(items.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Sample need queue not empty!!!");
        return items[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] indice;
        // private Item[] copyArray = (Item[]) new Object();
        private int index;


        public RandomizedQueueIterator() {
            indice = new int[size];
            // 注意这里不要将原数组复制成一个新数组，这样会导致原resize()方法失效
            for (int i = 0; i < size; i++) {
                indice[i] = i;
            }
            // for (int i = 0; i < size; i++) {
            //     copyArray[i] = items[i];
            // }
            // StdRandom.shuffle(copyArray);
            StdRandom.shuffle(indice);
            index = 0;
            // index = size - 1;
        }

        public boolean hasNext() {
            return index != indice.length;
            // index >= 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("Iterator must have next!!!!");
            return items[indice[index++]];
            // return copyArray[index--];
        }

        public void remove() {
            throw new UnsupportedOperationException("No support remove!!!");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }

        StdOut.println("first iterator");

        for (String s : rq) {
            StdOut.print(s + " ");
        }

        StdOut.println("");
        StdOut.println("second iterator ");

        for (String s : rq) {
            StdOut.print(s + " ");
        }
        StdOut.println("");

        for (int i = 0; i < 18; i++) {
            StdOut.print("deque ");
            StdOut.print(rq.dequeue());
            StdOut.println(" remain " + rq.size() + " elements. now capacity");
        }

    }

}