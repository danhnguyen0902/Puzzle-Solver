import java.util.Queue;
import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayDeque;

// -------------------------------------------------------------------------
/**
 * The puzzle is solved by using BFS and a regular FIFO Queue
 *
 * @author Danh
 * @version Mar 29, 2014
 */
public class BFS
{
    private Queue<State>            queue;
    private ArrayDeque<State>       visitedStates; // for output
    private NodeStore<State, State> hashTable;


    // ----------------------------------------------------------
    /**
     * Create a new BFS_FIFO_Queue object.
     *
     * @param queueType
     *            type of the queue used to solve the puzzle
     */
    public BFS(String queueType)
    {
        if (queueType.charAt(0) == 'F')
        {
            // FIFO Queue
            queue = new ArrayDeque<State>();
        }
        else
        {
            DistMetricComp comparator = new DistMetricComp();

            // Priority Queue
            queue = new PriorityQueue<State>(1000, comparator);
        }

        hashTable = new NodeStore<State, State>();
        visitedStates = new ArrayDeque<State>();
    }


    // ----------------------------------------------------------
    /**
     * Solve the puzzle using BFS
     *
     * @param initialState
     *            the state of the initial puzzle configuration
     * @param goalState
     *            the state of the desired puzzle configuration
     */
    public void bfs(State initialState, State goalState)
    {
        hashTable.put(initialState, null);
        queue.add(initialState);
        visitedStates.add(initialState);

        int i;
        State currentState;
        State newState;
        boolean done = false;
        while (!hashTable.containsKey(goalState))
        {
            currentState = queue.remove();
            List<State> neighbor = currentState.getNeighborStates();

            for (i = 0; i < neighbor.size(); ++i)
            {
                newState = neighbor.get(i);

                // We already reached this state
                if (hashTable.containsKey(newState))
                {
                    continue;
                }

                // ...Otherwise
                queue.add(newState);
                visitedStates.add(newState);
                hashTable.put(newState, currentState);

                if (newState.equals(goalState))
                {
                    done = true;
                    break;
                }
            }

            if (done)
            {
                break;
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Print the found result after applying the algorithm
     *
     * @param initialState
     *            the state of the initial puzzle configuration
     * @param goalState
     *            the state of the desired puzzle configuration
     * @param outputType
     *            the type of the output: visited states or solution path
     * @param verboseSetting
     *            the verbose mode: human readable or quiet
     */
    public void printResult(
        State initialState,
        State goalState,
        String outputType,
        String verboseSetting)
    {
        // Sequence of states visited
        if (outputType.charAt(0) == 'V')
        {
            printVisitedStates(verboseSetting);
        }
        else
        // Solution sequence of states
        {
            printSolutionPath(initialState, goalState, verboseSetting);
        }
    }


    // ----------------------------------------------------------
    /**
     * Helper method for printResutl() to print the visited states using BFS
     * algorithm
     *
     * @param verboseSetting
     *            the verbose mode: human readable or quiet
     */
    private void printVisitedStates(String verboseSetting)
    {
        int i;
        State tmp;

        System.out.println("Visited States:");

        i = 1;
        while (!visitedStates.isEmpty())
        {
            tmp = visitedStates.pop();
            System.out.println(i + " (" + tmp.getRowHole() + ","
                + tmp.getColHole() + ")");
            ++i;

            // If it's Verbose mode
            if (verboseSetting.charAt(0) == 'V')
            {
                System.out.println();
                tmp.printGridConfiguration();
                System.out.println();
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Helper method for printResutl() to print the solution path using BFS
     * algorithm
     *
     * @param initialState
     *            the state of the initial puzzle configuration
     * @param goalState
     *            the state of the desired puzzle configuration
     * @param verboseSetting
     *            the verbose mode: human readable or quiet
     */
    private void printSolutionPath(
        State initialState,
        State goalState,
        String verboseSetting)
    {
        int i;
        int j;
        State tmp;

        System.out.println("Solution path:");

        // Get the solution path
        ArrayDeque<State> result = new ArrayDeque<State>();
        ArrayDeque<State> copy = new ArrayDeque<State>();

        tmp = goalState;
        while (hashTable.get(tmp) != null)
        {
            result.push(tmp);
            copy.push(tmp);
            tmp = hashTable.get(tmp);
        }
        result.push(initialState);
        copy.push(tmp);

        String[] dir = new String[copy.size() - 1];
        int[] diff = new int[copy.size() - 1];

        // Prepare the answers
        prepare(initialState, goalState, copy, dir, diff);

        // Print them out
        i = 1;
        j = 0;
        while (!result.isEmpty())
        {
            tmp = result.pop();
            System.out.print(i + " (" + tmp.getRowHole() + ","
                + tmp.getColHole() + ")");
            if (!result.isEmpty()) // j < dir.length
            {
                System.out.print(" " + dir[j]);

                if (dir[j].equals("left") || dir[j].equals("right"))
                {
                    System.out.print(" "
                        + tmp.getCell(tmp.getRowHole() - 1, tmp.getColHole()
                            - 1 + diff[j]));
                }
                else
                {
                    System.out.print(" "
                        + tmp.getCell(
                            tmp.getRowHole() - 1 + diff[j],
                            tmp.getColHole() - 1));
                }
                ++j;
            }
            ++i;

            System.out.println();

            // If it's Verbose mode
            if (verboseSetting.charAt(0) == 'V')
            {
                System.out.println();
                tmp.printGridConfiguration();
                System.out.println();
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Helper method for printSolutionPath() to prepare answers for the outputs
     *
     * @param initialState
     *            the state of the initial puzzle configuration
     * @param goalState
     *            the state of the desired puzzle configuration
     * @param copy
     *            the copy of the result path
     * @param dir
     *            first component of the prepared answers
     * @param diff
     *            second component of the prepared answers
     */
    private void prepare(
        State initialState,
        State goalState,
        ArrayDeque<State> copy,
        String[] dir,
        int[] diff)
    {
        State tmp;
        State tmp2;
        int i = 0;

        tmp = copy.pop();
        while (!copy.isEmpty())
        {
            tmp2 = copy.pop();

            if (tmp.getRowHole() == tmp2.getRowHole())
            {
                if (tmp.getColHole() - 1 == tmp2.getColHole())
                {
                    dir[i] = "left";
                    diff[i] = -1;
                    ++i;
                }
                else
                {
                    dir[i] = "right";
                    diff[i] = 1;
                    ++i;
                }
            }
            else
            {
                if (tmp.getRowHole() - 1 == tmp2.getRowHole())
                {
                    dir[i] = "up";
                    diff[i] = -1;
                    ++i;
                }
                else
                {
                    dir[i] = "down";
                    diff[i] = 1;
                    ++i;
                }
            }

            tmp = tmp2;
        }
    }
}
