import java.util.Iterator;


/**
 * Created by jia on 8/9/17.
 */
public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private final Item item;
        private Node next;
        private Node prev;
        public Node(Item i, Node n, Node p) {
            next = n;
            prev = p;
            item = i;

        }
    }

    private final Node sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("cannot add a null item!");
        } else {
            sentinel.next = new Node(item, sentinel.next,sentinel);
            if (size == 0){
                sentinel.next.next = sentinel;
                sentinel.prev = sentinel.next;
            } else sentinel.next.next.prev = sentinel.next;
            size += 1;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("cannot add a null item!");
        } else {
            sentinel.prev = new Node(item, sentinel, sentinel.prev);
            sentinel.prev.prev.next = sentinel.prev;
            size += 1;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty!");
        } else {
            Item removedItem = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty!");
        } else {
            Item removedItem = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return removedItem;
        }
    }

    // return an iterator over items in order from front to end
    private class MyIterator implements Iterator<Item> {
        private Node temp;

        public MyIterator() {
            temp = sentinel;
        }

        @Override
        public Item next() {
            Item item;
            if (!this.hasNext()) {
                throw new java.util.NoSuchElementException("No more Element");
            } else {
              item = temp.next.item;
              temp = temp.next;
            }
            return item;
        }

        @Override
        public boolean hasNext() {
            return temp.next != sentinel;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("No such method!");
        }
    }

    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<Integer> deque = new Deque<>();
 //       tesy.addLast(2);
        deque.addFirst(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        deque.isEmpty();
        deque.isEmpty();
        deque.addFirst(7);
        deque.addFirst(8);
        deque.addFirst(9);

        System.out.println(deque.removeLast());
    }
}
