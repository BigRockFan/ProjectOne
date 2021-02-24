// --== CS400 File Header Information ==--
// Name: Rohan Putcha
// Email: rputcha@wisc.edu
// Team: Red
// Group: JC
// TA: Xinyi Liu
// Lecturer: Gary Dahl
// Notes to Grader: none

/**
 * A generic pair of two types to create a key-value pair
 * @param <KeyType> the key part of the pair
 * @param <ValueType> the value part of the pair
 */
public class Pair<KeyType, ValueType> {
    // two private variables that define the key-value pair
    private KeyType key;
    private ValueType value;

    /**
     * The constructor assigns the key and value attributes from given values
     * @param key the key to be assigned
     * @param value the value to be assigned
     */
    public Pair(KeyType key, ValueType value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key
     * @return the key
     */
    public KeyType getKey() {
        return key;
    }

    /**
     * Gets the value
     * @return the value
     */
    public ValueType getValue() {
        return value;
    }

    /**
     * Used to create a string representation of the pair in order to test the functionality
     * of many methods in TestHashTable
     * @return a string representation of the key-value pair
     */
    @Override
    public String toString() {
        return "Pair{key=" + key + ", value=" + value + '}';
    }
}
