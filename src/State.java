import java.util.List;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Represents a state of a specific puzzle configuration
 *
 * @author Danh
 * @version Mar 29, 2014
 */
public class State
{
    private int[] configuration;

    private int[] row = { -1, 0, 1, 0 };
    private int[] col = { 0, 1, 0, -1 };

    private int   rowHole;
    private int   colHole;

    private int   distanceMetric;


    // ----------------------------------------------------------
    /**
     * Create a new State object.
     *
     * @param configuration
     *            the given puzzle configuration
     */
    public State(int[] configuration)
    {
        this.configuration = new int[configuration.length];
        for (int i = 0; i < configuration.length; ++i)
        {
            this.configuration[i] = configuration[i];
        }

        // Find the position of the hole
        rowHole = -1;
        colHole = -1;
        findTheHole();

        // Compute the distance metric
        distanceMetric = 0;
        computeDistanceMetric();
    }


    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof State))
        {
            return false;
        }

        // If it is only one object
        if (obj == this)
        {
            return true;
        }

        boolean check = true;
        for (int i = 0; i < this.configuration.length; ++i)
        {
            if (this.configuration[i] != ((State)obj).configuration[i])
            {
                check = false;
                break;
            }
        }

        return check;
    }


    @Override
    public int hashCode()
    {
        // Preparation:
        long mod = 1 << 31 - 1; // maximum signed 32-bit: 2^31 - 1
        long[] twoPowerMod = new long[64];
        twoPowerMod[0] = 1;
        for (int i = 1; i < 64; ++i)
        {
            twoPowerMod[i] = (twoPowerMod[i - 1] * 2) % mod;
        }

        // Declaration:
        StringBuilder binaryString = new StringBuilder("");

        // Convert each cell into a 4-bit binary number and put them together
        for (int i = 0; i < this.configuration.length; ++i)
        {
            binaryString.append(convertTo4BitBinary(this.configuration[i]));
        }

        // Finally, compute the hash values:
        long result = 0; // maximum signed 64-bit: 2^63 - 1
        int length = binaryString.length();
        for (int i = length - 1; i >= 0; --i)
        {
            if (binaryString.charAt(i) == '1')
            {
                result = (result + twoPowerMod[length - 1 - i]) % mod;
            }
        }

        return (int)(result);
    }


    // ----------------------------------------------------------
    /**
     * Prints out the puzzle configuration in terms of grid
     */
    public void printGridConfiguration()
    {
        int[][] matrix = toMatrix(this.configuration);

        for (int i = 0; i < 4; ++i)
        {
            if (matrix[i][0] == 0)
            {
                System.out.printf("  ");
            }
            else
            {
                System.out.printf("%2d", matrix[i][0]);
            }
            for (int j = 1; j < 4; ++j)
            {
                if (matrix[i][j] == 0)
                {
                    System.out.printf("   ");
                }
                else
                {
                    System.out.printf("%3d", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the configuration of the puzzle with the current state
     *
     * @return puzzle configuration
     */
    public int[] getConfiguration()
    {
        return this.configuration;
    }


    // ----------------------------------------------------------
    /**
     * Computes the neighbor states of the current state
     *
     * @return return the neighbor states of the current state
     */
    public List<State> getNeighborStates()
    {
        List<State> neighbor = new ArrayList<State>();

        int[][] matrix = toMatrix(this.configuration);

        int i;
        int j;
        int k;
        int newRow;
        int newCol;
        int[][] newMatrix = new int[4][4];

        for (i = 0; i < 4; ++i)
        {
            for (j = 0; j < 4; ++j)
            {
                if (matrix[i][j] == 0)
                {
                    for (k = 0; k < 4; ++k)
                    {
                        newRow = i + row[k];
                        newCol = j + col[k];

                        if (check(matrix, newRow, newCol))
                        {
                            copyMatrix(matrix, newMatrix);
                            swap(newMatrix, i, j, newRow, newCol);
                            neighbor.add(new State(toConfiguration(newMatrix)));
                        }
                    }
                }
            }
        }

        return neighbor;
    }


    // ----------------------------------------------------------
    /**
     * Returns the row position of the hole
     *
     * @return the rowHole
     */
    public int getRowHole()
    {
        return rowHole;
    }


    // ----------------------------------------------------------
    /**
     * Returns the column position of the hole
     *
     * @return the colHole
     */
    public int getColHole()
    {
        return colHole;
    }


    // ----------------------------------------------------------
    /**
     * Returns the cell of the puzzle configuration given its position
     *
     * @param i
     *            the row of the cell
     * @param j
     *            the column of the cell
     * @return the desired cell
     */
    public int getCell(int i, int j)
    {
        int[][] matrix = toMatrix(this.configuration);

        return matrix[i][j];

    }


    // ----------------------------------------------------------
    /**
     * Get the distance metric
     *
     * @return the distance metric
     */
    public int getDistanceMetric()
    {
        return distanceMetric;
    }


    // ----------------------------------------------------------
    /**
     * Convert the given number to a 4-bit binary number and put it to a string
     *
     * @param number
     *            the given decimal number
     * @return the 4-bit binary number in a string
     */
    private StringBuilder convertTo4BitBinary(int number)
    {
        int tmp = number;
        StringBuilder result = new StringBuilder("");
        int[] bit = new int[4];

        // Convert to binary number
        int i = 3;
        while (tmp > 0)
        {
            bit[i] = tmp % 2;
            tmp = tmp / 2;
            --i;
        }

        // Put it to a string
        for (i = 0; i < 4; ++i)
        {
            result.append(bit[i]);
        }

        return result;
    }


    // ----------------------------------------------------------
    /**
     * Transforms the configuration to a matrix
     *
     * @param config
     *            the given configuration
     * @return the result matrix
     */
    private int[][] toMatrix(int[] config)
    {
        int[][] matrix = new int[4][4];
        int k = 0;

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                matrix[i][j] = config[k];
                ++k;
            }
        }

        return matrix;
    }


    // ----------------------------------------------------------
    /**
     * Transforms the matrix form to 1D-array configuration
     *
     * @param matrix
     *            the given matrix
     * @return the result configuration
     */
    private int[] toConfiguration(int[][] matrix)
    {
        int[] config = new int[16];
        int k = 0;

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                config[k] = matrix[i][j];
                ++k;
            }
        }

        return config;
    }


    // ----------------------------------------------------------
    /**
     * Check if the next move of zero is legal
     *
     * @param matrix
     *            the matrix form of the current configuration
     * @param i
     *            new row position of zero
     * @param j
     *            new column position of zero
     * @return true if it's legal, false otherwise
     */
    private boolean check(int[][] matrix, int i, int j)
    {
        return (0 <= i && i <= 3 && 0 <= j && j <= 3);
    }


    // ----------------------------------------------------------
    /**
     * Swap the two given positions in the given matrix
     *
     * @param matrix
     *            the new matrix form of the current configuration * @param
     *            newRow new row position of zero
     * @param i
     *            old row position of zero
     * @param j
     *            old column position of zero
     * @param newRow
     *            new row position of zero
     * @param newCol
     *            new column position of zero
     */
    private void swap(int[][] matrix, int i, int j, int newRow, int newCol)
    {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[newRow][newCol];
        matrix[newRow][newCol] = tmp;
    }


    // ----------------------------------------------------------
    /**
     * Copy the content of the old matrix over the new matrix
     *
     * @param oldMatrix
     *            the old matrix
     * @param newMatrix
     *            the new matrix
     */
    private void copyMatrix(int[][] oldMatrix, int[][] newMatrix)
    {
        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                newMatrix[i][j] = oldMatrix[i][j];
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Find position of the hole/zero
     */
    private void findTheHole()
    {
        int[][] matrix = toMatrix(this.configuration);
        boolean done = false;

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                if (matrix[i][j] == 0)
                {
                    rowHole = i + 1;
                    colHole = j + 1;
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
     * Compute the distance metric
     */
    private void computeDistanceMetric()
    {
        int i;
        int j;
        int u;
        int v;
        boolean flag;
        int[] config = new int[16];
        for (i = 0; i < 15; ++i)
        {
            config[i] = i + 1;
        }
        config[15] = 0;

        int[][] currentMatrix = toMatrix(this.configuration);
        int[][] goalMatrix = toMatrix(config);

        for (i = 0; i < 4; ++i)
        {
            for (j = 0; j < 4; ++j)
            {
                if (currentMatrix[i][j] == 0)
                {
                    continue;
                }

                flag = false;

                for (u = 0; u < 4; ++u)
                {
                    for (v = 0; v < 4; ++v)
                    {
                        if (goalMatrix[u][v] == currentMatrix[i][j])
                        {
                            distanceMetric += Math.abs(u - i) + Math.abs(v - j);
                            flag = true;
                            break;
                        }
                    }

                    if (flag)
                    {
                        break;
                    }
                }
            }
        }
    }
}
