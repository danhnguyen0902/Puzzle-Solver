//import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than the instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

// -------------------------------------------------------------------------
/**
 * The main program that reads input and prints output
 *
 * @author Danh
 * @version Mar 29, 2014
 */
public class Solver
{
    private static String outputType;
    private static String queueType;
    private static String verboseSetting;
    private static int[]  initialConfiguration;


    // ----------------------------------------------------------
    /**
     * Read the inputs: puzzle and requirements
     *
     * @param args
     *            arguments from the command line
     */
    public static void readInput(String[] args)
    {
        //Scanner scan = new Scanner(System.in);
        initialConfiguration = new int[16];

        //outputType = scan.next();
        //queueType = scan.next();
        //verboseSetting = scan.next();
        outputType = args[0];
        queueType = args[1];
        verboseSetting = args[2];

        for (int i = 0; i < 16; ++i)
        {
            //initialConfiguration[i] = scan.nextInt();
            initialConfiguration[i] = Integer.parseInt(args[i + 3]);
        }
        //scan.close();
    }


// ----------------------------------------------------------
    /**
     * Solve the given puzzle
     */
    public static void solveThePuzzle()
    {
        // Create the initial state:
        State initialState = new State(initialConfiguration);

        // Create the goal state:
        int i;
        int[] goalConfiguration = new int[16];
        for (i = 0; i < initialConfiguration.length - 1; ++i)
        {
            goalConfiguration[i] = i + 1;
        }
        goalConfiguration[initialConfiguration.length - 1] = 0;
        State goalState = new State(goalConfiguration);

        // Solve the puzzle:
        BFS puzzleSolver = null;

        puzzleSolver = new BFS(queueType);
        puzzleSolver.bfs(initialState, goalState);
        puzzleSolver.printResult(
            initialState,
            goalState,
            outputType,
            verboseSetting);
    }


    // ----------------------------------------------------------
    /**
     * The program starts from here
     *
     * @param args
     *            arguments from the command line
     */
    public static void main(String[] args)
    {
        readInput(args);

        solveThePuzzle();
    }
}
