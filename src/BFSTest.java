import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test cases for the NodeStore class
 *
 * @author Danh
 * @version Apr 2, 2014
 */
public class BFSTest
    extends TestCase
{
    private int[]                   config1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 0, 13, 14, 15              };
    private int[]                   config2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0,
        11, 10, 13, 14, 15, 12             };

    private int[]                   config3 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        11, 12, 13, 14, 15, 0             };

    private State                   state1;
    private State                   state2;
    private State                   state3;

    private BFS fifoBFS;
    private BFS priorBFS;

    // ----------------------------------------------------------
    /**
     * Sets up a beginning for each test case.
     */
    public void setUp()
    {
        state1 = new State(config1);
        state2 = new State(config2);
        state3 = new State(config3);

        fifoBFS = new BFS("F");
        priorBFS = new BFS("P");
    }


    // ----------------------------------------------------------
    /**
     * Tests the bfs() function
     */
    public void testBfs()
    {
        fifoBFS.bfs(state3, state3);
        priorBFS.bfs(state3, state3);
        assertFalse(state1.equals(state2));
    }

 // ----------------------------------------------------------
    /**
     * Tests the printResult() function
     */
    public void testPrintResult()
    {
        fifoBFS.bfs(state1, state3);
        fifoBFS.printResult(state1, state3, "S", "Q");
        assertTrue(state1.equals(state1));
    }
}
