import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * Created by jia on 8/8/17.
 */

// Deuqe is a Resizing Array.

public class RandomizedQueue<Item> implements Iterable<Item> {
    // construct an empty deque

    private int size;
    private Item[] randomQueue;

    public RandomizedQueue() {
        randomQueue = (Item[]) new Object[2];
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("Cannot add a null item!");
        } else {
            if (size == randomQueue.length) {
                Item[] tempDeque = (Item[]) new Object[randomQueue.length * 2];
                System.arraycopy(randomQueue,0, tempDeque, 0,size);
                randomQueue = tempDeque;
            }
            randomQueue[size] = item;
            size += 1;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        Item removedItem;
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty!");
        } else {
            int index = StdRandom.uniform(size);
            removedItem = randomQueue[index];
            randomQueue[index] = randomQueue[size - 1];
            randomQueue[size - 1] = null;

            if (size * 4 <= randomQueue.length) {
                Item[] tempDeque = (Item[]) new Object[randomQueue.length / 2];
                System.arraycopy(randomQueue, 0, tempDeque, 0, size);
                randomQueue = tempDeque;
            }
            size -= 1;
            return removedItem;
        }
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Queue is empty!");
        } else {
            int index = StdRandom.uniform(size);
            Item displayedItem = randomQueue[index];
            return displayedItem;
        }
    }

    // return an independent iterator over items in random order
    private class MyIterator implements Iterator<Item> {
        private int num = 0;
        private Item[] outputQueue = (Item[]) new Object[randomQueue.length];

        public MyIterator() {
            Item temp;
            int index;
            for (int i = 0; i < size; i++) {
                outputQueue[i] = randomQueue[i];
            }
            for (int i = 0; i < size; i++) {
                index = StdRandom.uniform(i + 1);
                temp = outputQueue[i];
                outputQueue[i] = outputQueue[index];
                outputQueue[index] = temp;
            }
        }

        @Override
        public Item next() {
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException("No more Element");
            } else {
                Item item = outputQueue[num];
                num += 1;
                return item;
            }
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException("No such method!");
        }

        @Override
        public boolean hasNext() {
            return num < size;
        }
    }

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    public static void main(String[] args) {
        // unit testing (optional)

        RandomizedQueue<Integer> test = new RandomizedQueue<>();
        test.enqueue(1);
        test.dequeue();

        test.enqueue(2);
        test.dequeue();

        test.enqueue(3);
        test.enqueue(4);
        test.enqueue(5);
        test.enqueue(6);
        test.enqueue(7);
        test.enqueue(8);
        test.enqueue(9);
        test.enqueue(10);
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();
        test.dequeue();

        test.enqueue(1);
    }
}