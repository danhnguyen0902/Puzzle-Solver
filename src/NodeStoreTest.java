import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test cases for the NodeStore class
 *
 * @author Danh
 * @version Apr 2, 2014
 */
public class NodeStoreTest
    extends TestCase
{
    private int[]                   config1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 0, 13, 14, 15              };
    private int[]                   config2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
        11, 10, 13, 14, 15, 12             };

    private State                   state1;
    private State                   state2;

    private NodeStore<State, State> hashTable;


    // ----------------------------------------------------------
    /**
     * Sets up a beginning for each test case.
     */
    public void setUp()
    {
        state1 = new State(config1);
        state2 = new State(config2);

        hashTable = new NodeStore<State, State>();
    }


    // ----------------------------------------------------------
    /**
     * Tests the containsKey() function
     */
    public void testContainsKey()
    {
        assertFalse(hashTable.containsKey(state1));

        hashTable.put(state1, state2);
        assertTrue(hashTable.containsKey(state1));
    }


    // ----------------------------------------------------------
    /**
     * Tests the put() function
     */
    public void testPut()
    {
        hashTable.put(state1, state2);
        hashTable.put(state2, null);
        assertTrue(hashTable.containsKey(state2));
    }


    // ----------------------------------------------------------
    /**
     * Tests the get() function
     */
    public void testGet()
    {
        hashTable.put(state1, null);
        assertEquals(null, hashTable.get(state2));
    }
}
