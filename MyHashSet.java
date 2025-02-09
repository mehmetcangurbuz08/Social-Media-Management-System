import java.util.Iterator;
// Create a Hashset Class with implementing Iterable for using necessary methods.
public class MyHashSet<E> implements Iterable<E> {
    private MyHashMap<E, Object> hashMap;  //Create a MyHashMap used to store the elements of the set
    private static final Object  CURRENT = new Object(); // A constant value used for all entries in the MyHashMap
    //Constructor for MyHashSet
    public MyHashSet() {
        hashMap = new MyHashMap<>();
    }

    //Adds an element to the set
    public void add(E element) {
        hashMap.put(element, CURRENT);
    }

    // Removes an element from the set
    public void remove(E element) {
        hashMap.remove(element);
    }

    //Checks if an element is in the set
    public boolean contains(E element) {
        return hashMap.containsKey(element);
    }

    // Returns true if the set is empty anf false if it is not empty.
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    // Returns an iterator over the elements
    public Iterator<E> iterator() {
        // Return an iterator for the elements in the set
        return new Iterator<>() {
            // Create an iterator for the entries in the map
            private Iterator<MyHashMap.Entry<E, Object>> HashMapIterator = hashMap.iterator();

            // Check if there are more elements in the set
            @Override
            public boolean hasNext() {
                return HashMapIterator.hasNext();
            }

            // Return the next element in the set
            @Override
            public E next() {
                return HashMapIterator.next().key;
            }
        };
    }

}


