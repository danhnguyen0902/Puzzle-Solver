import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Test cases the Solver class
 *
 * @author Danh
 * @version Apr 2, 2014
 */
public class SolverTest
    extends TestCase
{
    // ----------------------------------------------------------
    /**
     * Sets up a beginning for each test case.
     */
    public void setUp()
    {
        Solver obj = new Solver();
        assertNotNull(obj);
    }


    // ----------------------------------------------------------
    /**
     * Tests the main() function
     */
    public void testMain()
    {
        // 1
        Solver obj = new Solver();
        assertNotNull(obj);

        // 2
        // setSystemIn("S F Q 1 2 3 4 5 6 7 8 9 10 11 12 13 0 14 15\n");
        String[] args1 =
            { "S", "F", "Q", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "0", "14", "15" };
        Solver.main(args1);
        assertTrue(systemOut().getHistory().contains(
            "Solution path:\n" + "1 (4,2) right 14\n" + "2 (4,3) right 15\n"
                + "3 (4,4)\n"));

        // 3
        String[] args2 =
        { "V", "P", "Q", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "0", "14", "15" };
        Solver.main(args2);
        assertTrue(systemOut().getHistory().contains(
            "Visited States:\n" + "1 (4,2)\n" + "2 (3,2)\n" + "3 (4,3)\n"
                + "4 (4,1)\n" + "5 (3,3)\n" + "6 (4,4)\n"));

        // 4
        String[] args3 =
        { "S", "F", "V", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "0", "14", "15" };
        Solver.main(args3);
        assertTrue(systemOut().getHistory().contains(
            "Solution path:\n" + "1 (4,2) right 14\n\n" + " 1  2  3  4\n"
                + " 5  6  7  8\n" + " 9 10 11 12\n" + "13    14 15\n\n"
                + "2 (4,3) right 15\n\n" + " 1  2  3  4\n" + " 5  6  7  8\n"
                + " 9 10 11 12\n" + "13 14    15\n\n" + "3 (4,4)\n\n"
                + " 1  2  3  4\n" + " 5  6  7  8\n" + " 9 10 11 12\n"
                + "13 14 15   \n\n"));

        // 5
        String[] args4 =
        { "S", "P", "V", "1", "2", "3", "4", "5", "6", "15", "8", "9", "10",
            "11", "7", "13", "14", "12", "0" };
        Solver.main(args4);

        // 6
        String[] args5 =
        { "S", "P", "Q", "1", "2", "3", "4", "5", "6", "15", "8", "9", "10",
            "11", "7", "13", "14", "12", "0" };
        Solver.main(args5);

        // 7
        String[] args6 =
        { "V", "P", "V", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "0", "14", "15" };
        Solver.main(args6);
        assertTrue(systemOut().getHistory().contains(
            "Visited States:\n" + "1 (4,2)\n\n" + " 1  2  3  4\n"
                + " 5  6  7  8\n" + " 9 10 11 12\n" + "13    14 15\n\n"
                + "2 (3,2)\n\n" + " 1  2  3  4\n" + " 5  6  7  8\n"
                + " 9    11 12\n" + "13 10 14 15\n\n" + "3 (4,3)\n\n"
                + " 1  2  3  4\n" + " 5  6  7  8\n" + " 9 10 11 12\n"
                + "13 14    15\n\n" + "4 (4,1)\n\n" + " 1  2  3  4\n"
                + " 5  6  7  8\n" + " 9 10 11 12\n" + "   13 14 15\n\n"
                + "5 (3,3)\n\n" + " 1  2  3  4\n" + " 5  6  7  8\n"
                + " 9 10    12\n" + "13 14 11 15\n\n" + "6 (4,4)\n\n"
                + " 1  2  3  4\n" + " 5  6  7  8\n" + " 9 10 11 12\n"
                + "13 14 15   \n\n"));
    }
}
