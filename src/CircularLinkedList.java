import java.util.Iterator;


/**
 * The CircularLinkedList class is a linked list which last node points to the very first node of the list.
 * @param <E> the type parameter
 * @implNote implements CircularLinkedListInterface and Iterable
 * @author Lance W.
 * @version 1.0
 */
public class CircularLinkedList<E> implements CircularLinkedListInterface<E>, Iterable<E> {

    /**
     * stores the front node
     */
    private Node<E> front;
    /**
     * size of the linked list
     */
    private int size;


    /**
     * Default CircularLinkedList constructor
     */
    public CircularLinkedList() {
        front = null;
        size = 0;
    }


    /**
     * Constructor that implements varargs
     * @param item add as many items into the circular linked list as desired
     */
    @SafeVarargs
    public CircularLinkedList(E... item) {
        Node<E> firstNode = new Node<>(item[0]);
        firstNode.next = front;
        front = firstNode;
        size++;

        Node<E> current = front;

        for (int itemIdx = 1; itemIdx < item.length; itemIdx++) {
            E e = item[itemIdx];

            current.next = new Node<>(e);
            current = current.next;
            
            size++;
        }
        current.next = firstNode;
    }


    /**
     * Retrieves a count of elements being maintained by the list.
     *
     * @return the size of the list (count of elements)
     */
    @Override
    public int getSize() {
        return size;
    }


    /**
     * Retrieves the data at the specified position in the list
     *
     * @param position 0-based index for the list; must be in the range 0 to size - 1
     * @return the data in the specified position in the list
     */
    @Override
    public E get(int position) {
        if (position >= size){
            throw new IllegalArgumentException(String.format("Position out of bounds for a CLL of size %d", size));
        }
        Node<E> current = front;
        for (int nodesPassed = 0; nodesPassed < position; nodesPassed++) {
            current = current.next;

        }
        return current.data;
    }


    /**
     * Adds a new node to the end of the list; by nature, this element's next will point to the first element
     *
     * @param value the element to add to the list
     */
    @Override
    public void add(E value) {
        if (front == null){
            addAtFront(value);
        } else {
            Node<E> current = front;
            for (int nodesPassed = 0; nodesPassed < size - 1; nodesPassed++) {
                current = current.next;
            }
            Node<E> newNode = new Node<>(value);
            newNode.next = front;
            current.next = newNode;

            size++;
        }
    }


    /**
     * Adds a new node at the front of the linked list - mainly used to add a node at the front of the list
     * @param value the element to add to the list
     */
    private void addAtFront(E value){
        Node<E> firstNode = new Node<>(value);
        firstNode.next = front;
        front = firstNode;
        size++;
    }


    /**
     * Removes the specified item from the list, if it exists there.
     *
     * @param value the element to remove from the list
     * @return true, if the element was found and removed; false, if not found or list is empty
     */
    @Override
    public boolean remove(E value) {
        Node<E> current = front;
        int idx = 0;

        while (current.data != value || idx < size){
            if (current.data == value) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
            idx++;
        }

        return false;
    }


    /**
     * Removes the node at the specified position in the list
     *
     * @param position position in the list; must be in range 0 to size - 1
     */
    @Override
    public void remove(int position) {
        if (position >= size){
            throw new IllegalArgumentException(
                    String.format("Position out of bounds for a CLL of size %s", size));
        }
        Node<E> current = front;
        for (int nodesPassed = 0; nodesPassed < position-1; nodesPassed++) {
            current = current.next;

        }
        current.next = current.next.next;
        size--;

    }


    /**
     * Retrieves an iterator over the linked list's elements. Do not do other list operations like add or remove
     * from within an iterator loop; the results are not guaranteed to function as you might expect
     *
     * @return a strongly typed iterator over elements in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new CircularLinkedListIterator();
    }


    /**
     * Node
     * @param <T> type parameter for the node
     */
    private static class Node<T> {
        /**
         * stores the next node
         */
        public Node<T> next;

        /**
         * the node's data
         */
        public T data;

        /**
         * Node constructor
         * @param data the data desired to create the node
         */
        public Node(T data){
            this.data = data;
            next = null;
        }
    }

    /**
     * The iterator for the circular linked list
     */
    private class CircularLinkedListIterator implements Iterator<E> {
        /**
         * The current node that the iterator is keeping track
         */
        public Node<E> current;

        /**
         * This returns a circular linked list iterator.
         */
        public CircularLinkedListIterator(){
            current = front;
        }


        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #current} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }


        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public E next() {
            E currentData = current.data;
            current = current.next;
            return currentData;
        }
    }
}
