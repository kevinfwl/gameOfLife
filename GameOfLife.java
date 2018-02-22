import java.util.Random;
/**
 * A model of conway's game of life. A grid of Cells are presented with a random pattern of alive Cells selected
 * Cells will turn on or off with each  generation following the given rules.
 * If the cell is alive, it will stay alive until the next generation if there are 2 or 3 alive neighbors
 * If the cell is dead, it will become alive in the next generation for 3 neighboring alive cells
 * 
 * @author Kevin Feng role number: 5
 * @version 1.0
 */
public class GameOfLife
{
    /**
     *The maximum x coordinate of the cell grid.
     */
    public static final int MAX_X_VALUE = 100;
    /**
     * The maximum y coordinate of the cell grid.
     */
    public static final int MAX_Y_VALUE = 30;
    /**
     * The time between each generation in milliseconds.
     */
    public static final int INTERVAL_TIME = 100; //milliseconds

    private static Cell [][] cellGrid;
    private static boolean [][] nextGenerationStatus;
    private static Random random = new Random();

    /**
     * Executes the game of Life with a random pattern of Cells.
     */
    public static void main(String[]startGame) throws InterruptedException
    {
        cellGrid = new Cell[MAX_X_VALUE][MAX_Y_VALUE];
        nextGenerationStatus = new boolean[MAX_X_VALUE][MAX_Y_VALUE];
        int aliveCount = 1;

        for (int x = 0 ; x < MAX_X_VALUE ; x ++ )
        {
            for(int y = 0 ; y < MAX_Y_VALUE ; y++)
            {
                cellGrid[x][y] = new Cell(false, x, y);
            }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++)
        }// end of for (int x = 0 ; x < MAX_X_VALUE ; x ++ )

        getPattern();

        print();

        Thread.sleep(INTERVAL_TIME);

        // check the number of alive Cells in the array cells; loop will exit when all the cells are dead 
        while (aliveCount != 0)
        {

            // intialize nextGenerationStatus array (as false) to start testing it 
            for (int x = 0 ; x < MAX_X_VALUE ; x ++ )
            {
                for(int y = 0 ; y < MAX_Y_VALUE ; y++)
                {
                    nextGenerationStatus[x][y] = false;
                }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++)
            }// end of for (int x = 0 ; x < MAX_X_VALUE ; x ++ )

            /*
             * count number of neighbors for each Cell in the array cell depending on the number of neighbors, determine whether the cell will die in 
             * the next generation
             */
            for (int x = 0 ; x < MAX_X_VALUE ; x ++ )
            {
                for(int y = 0 ; y < MAX_Y_VALUE ; y++)
                {
                    int neighbourCount = checkNeighbours(x,y);
                    determineNextGeneration (x,y, neighbourCount);
                }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++)
            }// end of for (int x = 0 ; x < MAX_X_VALUE ; x ++ )

            for (int y = 0 ; y < MAX_Y_VALUE ; y++ )
            {
                for(int x = 0 ; x < MAX_X_VALUE ; x++)
                {
                    cellGrid[x][y].setCellStatus(nextGenerationStatus[x][y]);
                }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++ ) 
            }// end of for (int x = 0 ; x < MAX_X_VALUE ; x++)

            print();

            Thread.sleep(INTERVAL_TIME);

            aliveCount = 0;

            // check number of cell that are alive
            for (int y = 0 ; y < MAX_Y_VALUE ; y++ )
            {
                for(int x = 0 ; x < MAX_X_VALUE ; x++)
                {
                    if (cellGrid[x][y].isAlive())
                    {
                        aliveCount ++;
                    }// end of if (cellGrid[x][y].isAlive())
                }// end of for (int x = 0 ; x < MAX_X_VALUE ; x++)
            }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++ )
        }// end of while(aliveCount != 0)

    }// end of method main(String[]startGame) throws InterruptedException

    /**
     * Checks and returns the number of alive neighbours of a particular cell in the cell grid of the game.
     * 
     * @param x the x coordinate of the cell in which its neighbours is to be checked 
     * @param y the y coordinate of the cell in which its neighbours is to be checked
     * 
     * @return the number of neighbours of the cell
     */
    public static int checkNeighbours (int x, int y)
    {   
        int neighborCount = 0;
        final int SMALLEST_X_TEST_INDEX = 0;
        final int SMALLEST_Y_TEST_INDEX = 0;
        final int GREATEST_X_TEST_INDEX = MAX_X_VALUE - 1;
        final int GREATEST_Y_TEST_INDEX = MAX_Y_VALUE - 1;

        if (cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX){
            if (cellGrid[x+1][y].isAlive())
            {
                neighborCount++;
            }// end of if(cellGrid[x+1][y].isAlive())
        }// end of if(cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX)
        if (cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX){
            if (cellGrid[x][y+1].isAlive())
            { 
                neighborCount++;
            }// end of if (cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX
        }// end of if (cellGrid[x][y+1].isAlive())
        if (cellGrid[x][y].getXCoordinate() > SMALLEST_X_TEST_INDEX){
            if (cellGrid[x-1][y].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x][y+1].isAlive())
        }// end of if (cellGrid[x][y+1].isAlive())
        if (cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX ){
            if (cellGrid[x][y-1].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x][y-1].isAlive())
        }// end of if  (cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX )
        if (cellGrid[x][y].getXCoordinate() > SMALLEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX){
            if (cellGrid[x-1][y-1].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x-1][y-1].isAlive())
        }// end of if (cellGrid[x][y].getXCoordinate() > SMALLEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX)
        if (cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX){
            if (cellGrid[x+1][y+1].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x+1][y+1].isAlive())
        }// end of if (cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX)
        if (cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX){
            if (cellGrid[x+1][y-1].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x+1][y-1].isAlive())
        }// end of if (cellGrid[x][y].getXCoordinate() < GREATEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() > SMALLEST_Y_TEST_INDEX)
        if (cellGrid[x][y].getXCoordinate() > SMALLEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX){
            if (cellGrid[x-1][y+1].isAlive())
            {
                neighborCount++;
            }// end of if (cellGrid[x-1][y+1].isAlive())
        }// end of if (cellGrid[x][y].getXCoordinate() > SMALLEST_X_TEST_INDEX && cellGrid[x][y].getYCoordinate() < GREATEST_Y_TEST_INDEX)
        return neighborCount;
    }//end of static method checkNeighbours (int x, int y)

    /**
     * Determines the status of the generation of a particular cell on the grid.
     * 
     * @param neighbourCount the number of neighbours of the cell whose status of its next generation is to be determined
     * @param x the x coordinate of the cell whose status of its next generation is to be determined
     * @param y the y coordinate of the cell whose status of its next generation is to be determined
     */
    //Determine the status of the next generation of cells
    private static void determineNextGeneration (int x, int y, int neighbourCount)
    {
        if (cellGrid[x][y].isAlive())
        {
            if (neighbourCount == 2 || neighbourCount == 3)
            {
                nextGenerationStatus[x][y] = true;
            }// end of if (neighbourCount == 2 || neighbourCount == 3)
        }
        else 
        {
            if (neighbourCount == 3)
            {
                nextGenerationStatus[x][y] = true;
            }// end of if (neighbourCount == 3)
        }// end of if (cellGrid[x][y].isAlive())
    }// end of static method determineNextGeneration (int x, int y, int neighbourCount)

    /**
     * Creates a pattern for the coordinate grid
     */
    public static void getPattern ()
    {
        cellGrid[1][6].setCellStatus(true);
        cellGrid[2][6].setCellStatus(true);
        cellGrid[1][7].setCellStatus(true);
        cellGrid[2][7].setCellStatus(true);

        cellGrid[11][6].setCellStatus(true);
        cellGrid[11][7].setCellStatus(true);
        cellGrid[11][8].setCellStatus(true);

        cellGrid[12][5].setCellStatus(true);
        cellGrid[12][9].setCellStatus(true);
        cellGrid[13][4].setCellStatus(true);
        cellGrid[13][10].setCellStatus(true);
        cellGrid[14][4].setCellStatus(true);
        cellGrid[14][10].setCellStatus(true);
        cellGrid[15][7].setCellStatus(true);
        cellGrid[16][5].setCellStatus(true);
        cellGrid[16][9].setCellStatus(true);

        cellGrid[17][8].setCellStatus(true);
        cellGrid[17][7].setCellStatus(true);
        cellGrid[17][6].setCellStatus(true);
        cellGrid[18][7].setCellStatus(true);

        cellGrid[21][4].setCellStatus(true);
        cellGrid[21][5].setCellStatus(true);
        cellGrid[21][6].setCellStatus(true);
        cellGrid[22][4].setCellStatus(true);
        cellGrid[22][5].setCellStatus(true);
        cellGrid[22][6].setCellStatus(true);

        cellGrid[23][3].setCellStatus(true);
        cellGrid[23][7].setCellStatus(true);
        cellGrid[25][3].setCellStatus(true);
        cellGrid[25][7].setCellStatus(true);
        cellGrid[25][2].setCellStatus(true);
        cellGrid[25][8].setCellStatus(true);

        cellGrid[35][4].setCellStatus(true);
        cellGrid[35][5].setCellStatus(true);
        cellGrid[36][4].setCellStatus(true);
        cellGrid[36][5].setCellStatus(true);

        cellGrid[50][6].setCellStatus(true);
        cellGrid[52][6].setCellStatus(true);
        cellGrid[51][7].setCellStatus(true);
        cellGrid[52][7].setCellStatus(true);

        cellGrid[61][6].setCellStatus(true);
        cellGrid[61][7].setCellStatus(true);
        cellGrid[61][8].setCellStatus(true);

        cellGrid[62][5].setCellStatus(true);
        cellGrid[62][9].setCellStatus(true);
        cellGrid[63][4].setCellStatus(true);
        cellGrid[63][10].setCellStatus(true);
        cellGrid[64][4].setCellStatus(true);
        cellGrid[64][10].setCellStatus(true);
        cellGrid[65][7].setCellStatus(true);
        cellGrid[66][5].setCellStatus(true);
        cellGrid[66][9].setCellStatus(true);

        cellGrid[67][8].setCellStatus(true);
        cellGrid[67][7].setCellStatus(true);
        cellGrid[67][6].setCellStatus(true);
        cellGrid[68][7].setCellStatus(true);
        cellGrid[71][4].setCellStatus(true);
        cellGrid[71][5].setCellStatus(true);
        cellGrid[71][6].setCellStatus(true);
        cellGrid[72][4].setCellStatus(true);
        cellGrid[72][5].setCellStatus(true);
        cellGrid[72][6].setCellStatus(true);

        cellGrid[73][3].setCellStatus(true);
        cellGrid[73][7].setCellStatus(true);
        cellGrid[75][3].setCellStatus(true);
        cellGrid[75][7].setCellStatus(true);
        cellGrid[75][2].setCellStatus(true);
        cellGrid[75][8].setCellStatus(true);

        cellGrid[85][4].setCellStatus(true);
        cellGrid[85][5].setCellStatus(true);
        cellGrid[86][4].setCellStatus(true);
        cellGrid[86][5].setCellStatus(true);

    }// end of static method getPattern (int patternNumber)

    /**
     * Prints out the grid of the game of life.
     */
    public static void print()
    {
        String cellArrayString = "";
        // concatenate intial symbols array of the Cells in a string and print it
        for (int y = 0 ; y < MAX_Y_VALUE ; y++ )
        {
            for(int x = 0 ; x < MAX_X_VALUE ; x++)
            {
                cellArrayString = cellArrayString + cellGrid[x][y].drawCell();
            }// end of for (int x = 0 ; x < MAX_X_VALUE ; x++)
            cellArrayString += "\n";
        }// end of for (int y = 0 ; y < MAX_Y_VALUE ; y++ )
        System.out.println('\f');
        System.out.println(cellArrayString);
    }
}// end of class GameOfLife
