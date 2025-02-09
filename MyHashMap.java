import java.util.Iterator;
// Create an implementation of a HashMap.
public class MyHashMap<K, V> implements Iterable<MyHashMap.Entry<K, V>> {
    // Create table where key value variables are stored
    private Entry<K, V>[] table;
    // The initial capacity of the hash map
    private int capacity = 64;
    // The current number of key value pairs in the map
    private int size = 0;
    // The load factor for rehashing the hash map
    private static final double LOAD_FACTOR = 0.5;


    // Entry class representing a key value pairs
    public static class Entry<K, V> {
        public K key;
        public V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }
    }

    public MyHashMap() {
        table = new Entry[capacity];
    }

    //Inserts a key-value pair into the hashmap
    public void put(K key, V value) {
        // Calculate the index for the key with using its hashcode
        int index = indexFor(hash(key.hashCode()));

        // Get the first entry at the given index
        Entry<K, V> currentEntry = table[index];

        // Loop the list at this index to check if the key is already in the list
        while (currentEntry != null) {
            // If the key is existed, refresh its value and return
            if (currentEntry.key.equals(key)) {
                // Update the value
                currentEntry.value = value;
                return;
            }
            // Move to the next entry in the list
            currentEntry = currentEntry.next;
        }

        // If the key does not exist, add a new entry to the table
        addEntry(key, value, index);
    }


    // Get a value associated with the key
    public V get(K key) {
        // Calculate the index for the given key using its hashcode
        int index = indexFor(hash(key.hashCode()));

        // Get the first entry at the calculated index
        Entry<K, V> currentEntry = table[index];

        // Loop the list at this index to find the key
        while (currentEntry != null) {
            // If the keys are equal, return the value
            if (currentEntry.key.equals(key)) {
                return currentEntry.value;
            }
            // Move to the next entry in the list
            currentEntry = currentEntry.next;
        }

        // If the key does not exist, return null
        return null;
    }


    // Removes a key-value pair from the map
    public void remove(K key) {
        // Calculate the index for the key using its hashcode
        int index = indexFor(hash(key.hashCode()));

        // Get the first entry in the list at the calculated index
        Entry<K, V> previousEntry = null;
        Entry<K, V> currentEntry = table[index];

        // Loop the list at the index to find the key
        while (currentEntry != null) {
            // Check if the current entry's key equals the key to be removed
            if (currentEntry.key.equals(key)) {
                if (previousEntry == null) {
                    // If the key is in the first entry, refresh the table to skip it
                    table[index] = currentEntry.next;
                } else {
                    // If the key is found in a later entry ,skip over it by refreshing the reference.
                    previousEntry.next = currentEntry.next;
                }

                // Decrement the size of the hash table
                size--;
                return;
            }

            // Move to the next entry in the list
            previousEntry = currentEntry;
            currentEntry = currentEntry.next;
        }
    }

    // Checks if a key is in the map or not
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    // Returns true if the map is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns an iterator over the entries
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }

    // Internal helper methods
    private void addEntry(K key, V value, int index) {
        // Get the existing entry at the given index
        Entry<K, V> existingEntry = table[index];

        // Create a new entry and insert it at the beginning of the list
        table[index] = new Entry<>(key, value, existingEntry);

        // Increase the size of the hash table
        size++;

        // Check if the load factor has been exceeded
        if (size >= capacity * LOAD_FACTOR) {
            // Double the capacity of the table and rehash all entries
            rehash(2 * capacity);
        }
    }


    // Create a method for refreshing map size
    private void rehash(int newCapacity) {
        // Get the old table for rehashing
        Entry<K, V>[] oldTable = table;

        // Create a new table with the refreshed capacity
        table = new Entry[newCapacity];
        capacity = newCapacity;

        // Reset the size since entries will be reinserted
        size = 0;

        // Reinsert each entry from the old table from the new table
        for (Entry<K, V> oldEntry : oldTable) {
            while (oldEntry != null) {
                // Reinsert the current entry into the new table
                put(oldEntry.key, oldEntry.value);
                // Move to the next entry
                oldEntry = oldEntry.next;
            }
        }
    }

    // Calculate the hash for a given hashCode
    private int hash(int hashCode) {
        return hashCode;
    }

    //Calculate the index in the hash table for a given hash value.
    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    // Iterator implementation for the HashMap
    private class HashMapIterator implements Iterator<Entry<K, V>> {
        int index = 0;
        Entry<K, V> next;

        HashMapIterator() {
            moveToNext();
        }

        private void moveToNext() {
            // Iterate through the table until a non-null element is found or the end is reached
            while (index < table.length) {
                next = table[index++];
                if (next != null) {
                    // Exit the loop once a non-null element is found
                    break;
                }
            }
        }

        //Checks if there are more elements to iterate over in the hash map.
        public boolean hasNext() {
            return next != null;
        }

        public Entry<K, V> next() {
            // Hold the current entry and move to the next one
            Entry<K, V> currentEntry = next;
            next = currentEntry.next; // Move to the next entry
            // If the next entry is null, move to the next bucket in the table
            if (next == null) {
                moveToNext();
            }
            // Return the current entry
            return currentEntry;
        }

    }
}