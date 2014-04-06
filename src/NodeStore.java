// -------------------------------------------------------------------------
/**
 * A step-size-of-1 linear probing hash table that stores the set of puzzle
 * states that have been already visited so far by the Breadth First Search
 * Algorithm.
 *
 * @param <Key>
 *            the key
 * @param <Value>
 *            the value
 * @author Danh
 * @version Apr 1, 2014
 */
public class NodeStore<Key, Value>
{
    private static final int INIT_CAPACITY = 10;

    private int              n;                   // number of key-value pairs
// in the hash table
    private int              m;                   // size of the hash table
    private Key[]            keys;
    private Value[]          vals;


    // ----------------------------------------------------------
    /**
     * Create a new NodeStore object.
     */
    public NodeStore()
    {
        this(INIT_CAPACITY);
    }


    // ----------------------------------------------------------
    /**
     * Create a new NodeStore object.
     *
     * @param capacity
     *            the initial capacity of the hash table
     */
    @SuppressWarnings("unchecked")
    public NodeStore(int capacity)
    {
        m = capacity;
        keys = (Key[])new Object[m];
        vals = (Value[])new Object[m];
    }


    // ----------------------------------------------------------
    /**
     * Check if a key-value pair with the given key exist in the hash table
     *
     * @param key
     *            the given key
     * @return true if it does, false otherwise
     */
    public boolean containsKey(Key key)
    {
        int i = hashValueOf(key);

        while (keys[i] != null)
        {
            if (keys[i].equals(key))
            {
                return true;
            }

            i = (i + 1) % m;
        }

        return false;
    }


    // ----------------------------------------------------------
    /**
     * Insert the key-value pair into the hash table
     *
     * @param key
     *            the given key
     * @param val
     *            the given value
     */
    public void put(Key key, Value val)
    {
        // Double the size of the hash table if it's half-full
        if (n >= m / 2)
        {
            resize(2 * m);
        }

        int i = hashValueOf(key);

        while (keys[i] != null)
        {
            i = (i + 1) % m;
        }

        keys[i] = key;
        vals[i] = val;
        n++;
    }


    // ----------------------------------------------------------
    /**
     * Return the value associated with the given key
     *
     * @param key
     *            the given key
     * @return the value, null if no such value
     */
    public Value get(Key key)
    {

        int i = hashValueOf(key);

        while (keys[i] != null)
        {
            if (keys[i].equals(key))
            {
                return vals[i];
            }

            i = (i + 1) % m;
        }

        return null;
    }


    // ----------------------------------------------------------
    /**
     * Compute the hash value of keys
     *
     * @param key
     *            the given key
     * @return value between 0 and M - 1
     */
    private int hashValueOf(Key key)
    {
        return (key.hashCode()) % m;
    }


    // ----------------------------------------------------------
    /**
     * Resize the hash table to the given capacity and re-hash all of the keys
     *
     * @param capacity
     *            the new capacity
     */
    private void resize(int capacity)
    {
        NodeStore<Key, Value> tmp = new NodeStore<Key, Value>(capacity);

        for (int i = 0; i < m; i++)
        {
            if (keys[i] != null)
            {
                tmp.put(keys[i], vals[i]);
            }
        }

        keys = tmp.keys;
        vals = tmp.vals;
        m = tmp.m;
    }

}
