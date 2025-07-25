import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {


    @org.junit.jupiter.api.Test
    void circularLinkedList() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,22);
        assertEquals(4, integers.getSize());
    }

    @org.junit.jupiter.api.Test
    void getSize() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,22);
        assertEquals(4, integers.getSize());
    }

    @org.junit.jupiter.api.Test
    void get() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        assertEquals(19, integers.get(2));
    }

    @org.junit.jupiter.api.Test
    void add() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        integers.add(29);
        assertEquals(29, integers.get(4));
    }

    @org.junit.jupiter.api.Test
    void addAtFront() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>();
        integers.add(29);
        assertEquals(29, integers.get(0));
    }

    @org.junit.jupiter.api.Test
    void removeValue() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        integers.add(29);
        assertTrue(integers.remove((Integer)19));
    }

    @org.junit.jupiter.api.Test
    void removeValueAndCheck() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        integers.add(29);
        integers.remove((Integer)29);
        assertEquals(25, integers.get(3));
    }


    @org.junit.jupiter.api.Test
    void removeAtPos() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        integers.add(29);
        integers.remove(2);
        assertEquals(25, integers.get(2));

    }

    @org.junit.jupiter.api.Test
    void iterator() {
        CircularLinkedList<Integer> integers = new CircularLinkedList<>(11,17,19,25);
        Iterator<Integer> cLLIteratorTest = integers.iterator();

        assertTrue(cLLIteratorTest.hasNext());


    }
}