import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test cases for the State class
 *
 * @author Danh
 * @version Apr 2, 2014
 */
public class StateTest
    extends TestCase
{
    private int[] config1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 13, 14,
        15               };
    private int[] config2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 11, 10, 13, 14, 15,
        12               };

    private State state1;
    private State state2;


    // ----------------------------------------------------------
    /**
     * Sets up a beginning for each test case.
     */
    public void setUp()
    {
        state1 = new State(config1);
        state2 = new State(config2);
    }


    // ----------------------------------------------------------
    /**
     * Tests the equals() function
     */
    public void testEquals()
    {
        assertTrue(state1.equals(state1));
        assertFalse(state1.equals(state2));
        assertFalse(state1.equals(new Integer(3)));
    }


    // ----------------------------------------------------------
    /**
     * Tests the hashCode() function
     */
    public void testHashCode()
    {
        assertEquals(448531951, state1.hashCode());
        assertEquals(280682236, state2.hashCode());
    }


    // ----------------------------------------------------------
    /**
     * Tests the printGridConfiguration() function
     */
    public void testPrintGridConfiguration()
    {
        assertEquals(448531951, state1.hashCode());

        state1.printGridConfiguration();
        state2.printGridConfiguration();
    }


    // ----------------------------------------------------------
    /**
     * Tests the getConfiguration() function
     */
    public void testGetConfiguration()
    {
        int[] tmp = state2.getConfiguration();

        for (int i = 0; i < tmp.length; ++i)
        {
            assertEquals(tmp[i], config2[i]);
        }
    }


    // ----------------------------------------------------------
    /**
     * Tests the getNeighborStates() function
     */
    public void testGetNeighborStates()
    {
        List<State> list = state1.getNeighborStates();

        assertEquals(list.size(), 2);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getRowHole() function
     */
    public void testGetRowHole()
    {
        int row = state1.getRowHole();

        assertEquals(row, 4);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getColHole() function
     */
    public void testGetColHole()
    {
        int col = state1.getColHole();

        assertEquals(col, 1);
    }


    // ----------------------------------------------------------
    /**
     * Tests the getRowHole() function
     */
    public void testGetCell()
    {
        int row = state2.getCell(2, 3);

        assertEquals(row, 10);
    }
}
