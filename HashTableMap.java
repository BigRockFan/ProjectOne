// --== CS400 File Header Information ==--
// Name: Rohan Putcha
// Email: rputcha@wisc.edu
// Team: Red
// Group: JC
// TA: Xinyi Liu
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A hash table implementation where any generic value can be searched up using a generic key.
 * @param <KeyType> the variable type for the key
 * @param <ValueType> the variable type for the value
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
    // private variables
    private int capacity;
    private int load;
    private LinkedList<Pair<KeyType, ValueType>>[] hashArray;

    // constructors
    /**
     * Constructs the hash table's array with a provided capacity. Also instantiates the
     * linked lists that are within the array.
     * @param capacity the capacity of the hash table to be created
     */
    public HashTableMap(int capacity) {
        this.capacity = capacity;
        this.load = 0; // no elements yet
        // cast instantiated array of linked lists into an array of linked lists of generic type Pair:
        this.hashArray = (LinkedList<Pair<KeyType, ValueType>>[]) new LinkedList<?>[capacity];
        // the linked lists in the array are all set to null so clear() will instantiate them all:
        this.clear();
    }

    /**
     * Constructs the hash table's array with a default capacity of 10. Also instantiates the
     * linked lists that are within the array.
     */
    public HashTableMap() {
        // with default capacity = 10.
        this.capacity = 10;
        this.load = 0; // no elements yet
        // cast instantiated array of linked lists into an array of linked lists of generic type Pair:
        this.hashArray = (LinkedList<Pair<KeyType, ValueType>>[]) new LinkedList<?>[capacity];
        // the linked lists in the array are all set to null so clear() will instantiate them all:
        this.clear();
    }

    // methods defined in the MapADT interface
    /**
     * Puts a specified key-value pair into the hash table
     * @param key the key part of the pair to be added
     * @param value the value part of the pair to be added
     * @return true if the pair was added to the hash table, false if the key is null or already
     *         exists in the hash table
     */
    @Override
    public boolean put(KeyType key, ValueType value) {
        // first, ensure the key is not null/ does not already exist in the hash table
        if (key == null || containsKey(key))
            return false;

        // second, create key-value pair to be added
        Pair<KeyType, ValueType> pair = new Pair<KeyType, ValueType>(key, value);
        // store at (absolute value of your key's hashCode) modulus the HashTableMap's current capacity
        int indexToStore = Math.abs(key.hashCode()) % this.capacity;

        // third, add key-value pair and increase the load
        this.hashArray[indexToStore].add(pair);
        this.load++;

        // fourth, check if the load is >=85%:
        if (loadExceeded())
            // if it is >=85%, the array will be resized by doubling capacity and rehashing
            resizeArray();

        return true; // return true to indicate successfully adding pair
    }

    /**
     * Gets a value from the hash table, given a specific key to look it up.
     * @param key the key to be looked up in the hash table
     * @return the value that corresponds with the given key in the hash table
     * @throws NoSuchElementException if the provided key does not exist in the hash table
     */
    @Override
    public ValueType get(KeyType key) throws NoSuchElementException {
        // get the hash index of the given key
        int hashIndex = Math.abs(key.hashCode()) % this.capacity;

        // iterate through the linked list at this index to find the exact key
        for (Pair<KeyType, ValueType> pair : hashArray[hashIndex]) {
            if (key.equals(pair.getKey()))
                return pair.getValue();
        }

        // if the key was not found, it does not exist in the hash table so an exception is thrown
        throw new NoSuchElementException("Key not found in hash table");
    }

    /**
     * Returns the load of the hash table filled up (its current size)
     * @return the current load of the hash table
     */
    @Override
    public int size() {
        return load;
    }

    /**
     * Iterates through the hash table to locate a specific key and returns whether it exists.
     * @param key the key to be looked up in the hash table
     * @return true if the key is found in the hash table, false otherwise
     */
    @Override
    public boolean containsKey(KeyType key) {
        // iterate through all elements of hash table
        for (LinkedList<Pair<KeyType, ValueType>> list : hashArray) {
            for (Pair<KeyType, ValueType> pair : list) {
                // if the key value exists, report by returning true
                if (pair.getKey().equals(key))
                    return true;
            }
        }
        // key value was not found, so return false
        return false;
    }

    /**
     * Removes a value in the hash table by key. The index of the key is calculated and the
     * linked list at that index is traversed to locate and remove the value.
     * @param key the key in the key-value pair to be removed
     * @return the value that was removed from the hash table, or null if the key is not found
     */
    @Override
    public ValueType remove(KeyType key) {
        // get the hash index of the given key
        int hashIndex = Math.abs(key.hashCode()) % this.capacity;

        // set up value to be returned (will stay null if no key found)
        ValueType value = null;

        // iterate through the linked list at this index to find the exact key
        for (Pair<KeyType, ValueType> pair : hashArray[hashIndex]) {
            if (key.equals(pair.getKey())) {
                value = pair.getValue();
                hashArray[hashIndex].remove(pair);
                this.load--;
            }
        }

        // return removed (or null) value
        return value;
    }

    /**
     * Clears the contents of the hash table and resets the load to 0.
     */
    @Override
    public void clear() {
        // iterate through the hash array and re-instantiate each linked list, clearing it
        for (int i = 0; i < this.capacity; i++) {
            hashArray[i] = new LinkedList<>();
        }
        // reset the load
        this.load = 0;
    }

    // private helper methods:
    /**
     *  Calculates the load capacity and determines whether it is >=85%
     * @return true if the load capacity is >=85%, false otherwise
     */
    private boolean loadExceeded() {
        return (((double)load)/((double)capacity)) >= 0.85D;
    }

    /**
     * Resizes the array if the load on the hash table has been exceeded (>=85%).
     * The hash table is doubled in capacity and the elements are rehashed using
     * the new capacity. The indexes of each original element may change.
     */
    private void resizeArray() {
        // double the capacity variable to reflect new hash array to be made
        this.capacity *= 2;

        // create a new array with the new capacity
        LinkedList<Pair<KeyType, ValueType>>[] newHashArray;
        newHashArray = (LinkedList<Pair<KeyType, ValueType>>[]) new LinkedList<?>[this.capacity];

        // the linked lists in the array are all set to null so this loop will instantiate them all:
        for (int i = 0; i < this.capacity; i++) {
            newHashArray[i] = new LinkedList<>();
        }

        // iterate through each key-value pair in the hash table and rehash with the new capacity
        int indexToStore;
        for (LinkedList<Pair<KeyType, ValueType>> list : hashArray) {
            for (Pair<KeyType, ValueType> pair : list) {
                indexToStore = Math.abs(pair.getKey().hashCode()) % this.capacity;
                newHashArray[indexToStore].add(pair);
            }
        }

        // change private hashArray reference to the newly created array
        this.hashArray = newHashArray;
    }

    // additional public method for testing:
    /**
     * Returns the array holding the hash table in order to test its contents in the
     * TestHashTable class.
     * @return the private hash table array
     */
    public LinkedList<Pair<KeyType, ValueType>>[] getHashArray() {
        return hashArray;
    }
}
