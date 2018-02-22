import java.awt.*;
import javax.swing.*;   
import java.awt.event.*;

/**
 * A model of conway's game of life. A grid of Cells are presented on a frame window. Users may create their own 
 * pattern by clicking the buttons on the grid and execute the game of life according the the rules below:
 * 
 * Cells will turn on or off with each  generation following the given rules.
 * If the cell is alive, it will stay alive until the next generation if there are 2 or 3 alive neighbors
 * If the cell is dead, it will become alive in the next generation for 3 neighboring alive cells
 * 
 * @author Kevin Feng role number: 5
 * @version 1.0
 */
public class BetterGameOfLife
{
    private final int HIGHEST_POSSIBLE_GRID_VALUE = 501;
    private final int LOWEST_POSSIBLE_GRID_VALUE = 3;
    
    private Cell [][] cellGrid;
    private Container contentPane;
    private JFrame frame;
    private int intervalTime = 1000; //milliseconds
    private int maxXValue;
    private int maxYValue;
    private boolean [][] nextGenerationStatus;
    private Timer timer;

    /**
     * Creates a game of life with a default grid 20 by 20 Cells 
     * and the graphical user interface that goes with the grid
     */
    public BetterGameOfLife ()
    {
        maxXValue = 20;
        maxYValue = 20;

        cellGrid = new Cell[maxXValue][maxYValue];
        nextGenerationStatus = new boolean[maxXValue][maxYValue];

        frame = new JFrame();
        contentPane = frame.getContentPane();
        for (int x = 0 ; x < maxXValue ; x ++ )
        {
            for(int y = 0 ; y < maxYValue ; y++)
            {
                cellGrid[x][y] = new Cell(false, x, y);
                nextGenerationStatus[x][y] = false;
            }// end of for (int y = 0 ; y < maxYValue ; y++)
        }// end of for (int x = 0 ; x < maxXValue ; x ++ ) 

        timer = new Timer(intervalTime , new TimerActionListener());
        createGUI();
    }// end of constructor BetterGameOfLife()

    /**
     * Checks and returns the number of alive neighbours of a particular cell in the cell grid of the game.
     * 
     * @param x the x coordinate of the cell in which its neighbours is to be checked 
     * @param y the y coordinate of the cell in which its neighbours is to be checked
     * 
     * @return the number of neighbours of the cell
     */
    public int checkNeighbours (int x, int y)
    {   
        int neighborCount = 0;
        final int SMALLEST_X_TEST_INDEX = 0;
        final int SMALLEST_Y_TEST_INDEX = 0;
        final int GREATEST_X_TEST_INDEX = maxXValue - 1;
        final int GREATEST_Y_TEST_INDEX = maxYValue - 1;

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
     * Creates a frame with a menu bar and a default 20x20 set of 
     */
    public void createGUI ()
    {
        JMenuBar menuBar = new JMenuBar();

        frame.setJMenuBar(menuBar);

        JMenu gameOptions = new JMenu("Options");

        menuBar.add(gameOptions);

        JMenuItem startGame = new JMenuItem ("Start");
        JMenuItem stopGame = new JMenuItem ("Pause");
        JMenuItem resetGame = new JMenuItem("Reset");
        JMenuItem speedUp = new JMenuItem("Speed Up");
        JMenuItem slowDown = new JMenuItem ("Slow Down");

        gameOptions.add(startGame);
        gameOptions.add(stopGame);
        gameOptions.add(resetGame);
        gameOptions.add(speedUp);
        gameOptions.add(slowDown);

        startGame.addActionListener(new StartGame());
        stopGame.addActionListener(new StopGame());
        resetGame.addActionListener(new Reset());
        speedUp.addActionListener(new SpeedUp());
        slowDown.addActionListener(new SlowDown());

        JMenu changeDimensions = new JMenu("Dimensions");
        menuBar.add(changeDimensions);

        JMenuItem changeGrid = new JMenuItem("Change Dimensions");
        changeDimensions.add(changeGrid);

        changeGrid.addActionListener(new ChangeGrid());

        contentPane.setLayout(new GridLayout(maxXValue,maxYValue));
        print(); 

        frame.pack();
        frame.setVisible(true);
    }// end of method createGUI()

    /**
     * Determines the status of the generation of a particular cell on the grid.
     * 
     * @param neighbourCount the number of neighbours of the cell whose status of its next generation is to be determined
     * @param x the x coordinate of the cell whose status of its next generation is to be determined
     * @param y the y coordinate of the cell whose status of its next generation is to be determined
     */
    //Determine the status of the next generation of cells
    public void determineNextGeneration (int x, int y, int neighbourCount)
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
     * Prints out the grid of Cells of this game of life.
     */
    public void print()
    {   
        for (int x = 0 ; x < maxXValue ; x ++ )
        {
            for(int y = 0 ; y < maxYValue ; y++)
            {
                contentPane.add(cellGrid[x][y].drawButton());
            }// end of for (int y = 0 ; y < maxYValue ; y++)
        }// end of for (int x = 0 ; x < maxXValue ; x ++ ) 
        contentPane.setVisible(true);
    }// end of method  print()

    /**
     * changes the dimensions for this cell grid
     * 
     * @param xAxis the number of columns that this game of life have
     * @param yAxis the number of rows that this game of life have
     */
    public void setGameGrid (int xAxis, int yAxis)
    {
        if (xAxis > LOWEST_POSSIBLE_GRID_VALUE && xAxis < HIGHEST_POSSIBLE_GRID_VALUE)
        {
            maxXValue = xAxis;
        }// end of if (xAxis >= 4)
        if (yAxis > LOWEST_POSSIBLE_GRID_VALUE && yAxis < HIGHEST_POSSIBLE_GRID_VALUE)
        {
            maxYValue = yAxis;
        }// end of if (yAxis >= 4)
        contentPane.setVisible(false);

        cellGrid = new Cell[maxXValue][maxYValue];
        nextGenerationStatus = new boolean[maxXValue][maxYValue];

        for (int x = 0 ; x < maxXValue ; x ++ )
        {
            for(int y = 0 ; y < maxYValue ; y++)
            {
                cellGrid[x][y] = new Cell(false, x, y);
                nextGenerationStatus[x][y] = false;
            }// end of for (int y = 0 ; y < maxYValue ; y++)
        }// end of for (int x = 0 ; x < maxXValue ; x ++ )

        contentPane.setLayout(new GridLayout(xAxis,yAxis));
    }// end of mutator (int xAxis, int yAxis)

    /**
     * Executes the game of Life for one generation
     */
    public void startGame () throws InterruptedException
    {
        int aliveCount = 1;

        // intialize nextGenerationStatus array (as false) to start testing it 
        for (int x = 0 ; x < maxXValue ; x ++ )
        {
            for(int y = 0 ; y < maxYValue ; y++)
            {
                nextGenerationStatus[x][y] = false;
            }// end of for (int y = 0 ; y < maxYValue ; y++)
        }// end of for (int x = 0 ; x < maxXValue ; x ++ )

        /*
         * count number of neighbors for each Cell in the array cell depending on the number of neighbors, determine whether the cell will die in 
         * the next generation
         */
        for (int x = 0 ; x < maxXValue ; x ++ )
        {
            for(int y = 0 ; y < maxYValue ; y++)
            {
                int neighbourCount = checkNeighbours(x,y);
                determineNextGeneration (x,y, neighbourCount);
            }// end of for (int y = 0 ; y < maxYValue ; y++)
        }// end of for (int x = 0 ; x < maxXValue ; x ++ )

        for (int y = 0 ; y < maxYValue ; y++ )
        {
            for(int x = 0 ; x < maxXValue ; x++)
            {
                cellGrid[x][y].setCellStatus(nextGenerationStatus[x][y]);
            }// end of for (int y = 0 ; y < maxYValue ; y++ ) 
        }// end of for (int x = 0 ; x < maxXValue ; x++)

        print();

        aliveCount = 0;

        // check number of cell that are alive
        for (int y = 0 ; y < maxYValue ; y++ )
        {
            for(int x = 0 ; x < maxXValue ; x++)
            {
                if (cellGrid[x][y].isAlive())
                {
                    aliveCount ++;
                }// end of if (cellGrid[x][y].isAlive())
            }// end of for (int x = 0 ; x < maxXValue ; x++)
        }// end of for (int y = 0 ; y < maxYValue ; y++ )

        if (aliveCount == 0 )
        {
            timer.stop();
        }// end of if (aliveCount == 0 )
    }// end of method main(String[]startGame) throws InterruptedException

    //ActionListener classes

    //starts the timer (used for the startGame JMenuItem)
    private class ChangeGrid implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            boolean validXInput = false;
            boolean validYInput = false;
            int xAxis = 0;
            int yAxis = 0;
            String inputMessage = "Enter a integer value above 3 and below 501";
            String errorMessage = "Any Value entered in this prompt will not change the grid";
            String errorTitle = "ERROR: INPUT NOT INTEGER";
            String errorTileTwo = "ERROR: INTEGER OUT OF BOUNDS";

            try 
            {
                xAxis = Integer.parseInt(JOptionPane.showInputDialog (inputMessage, "X dimensions of the grid"));
                if (xAxis > LOWEST_POSSIBLE_GRID_VALUE && xAxis < HIGHEST_POSSIBLE_GRID_VALUE)
                {
                    validXInput = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(contentPane, errorMessage , errorTileTwo, JOptionPane.ERROR_MESSAGE );
                }// end of if (xAxis > LOWEST_POSSIBLE_GRID_VALUE && xAxis < HIGHEST_POSSIBLE_GRID_VALUE)
            }// end of try 
            catch (NumberFormatException h)
            {
                JOptionPane.showMessageDialog(contentPane,errorMessage , errorTitle, JOptionPane.ERROR_MESSAGE );
            }// end of catch (NumberFormatException h)

            try 
            {
                yAxis = Integer.parseInt(JOptionPane.showInputDialog (inputMessage, "Y dimensions of the grid"));
                if (yAxis > LOWEST_POSSIBLE_GRID_VALUE && yAxis < HIGHEST_POSSIBLE_GRID_VALUE)
                {
                    validYInput = true;
                }
                else
                {
                    JOptionPane.showMessageDialog(contentPane,errorMessage , errorTileTwo, JOptionPane.ERROR_MESSAGE );
                }// end of if (yAxis > LOWEST_POSSIBLE_GRID_VALUE && yAxis < HIGHEST_POSSIBLE_GRID_VALUE)
            }// end of try 
            catch (NumberFormatException h)
            {
                JOptionPane.showMessageDialog(contentPane,errorMessage , errorTitle, JOptionPane.ERROR_MESSAGE );
            }// end of catch (NumberFormatException h)

            if (validYInput && validXInput)
            {
                contentPane.removeAll();
                setGameGrid(xAxis, yAxis);
                print();
            }// end of if (validYInput && validXInput)
        }// end of methods actionPerformed(ActionEvent e)
    }// end of class ChangeGrid

    // ActionListener for the "reset" JMenuItem; resets the GUI for the Cells
    private class Reset implements ActionListener 
    {
        public void actionPerformed (ActionEvent e)
        {
            timer.stop();
            for (int x = 0 ; x < maxXValue ; x ++ )
            {
                for(int y = 0 ; y < maxYValue ; y++)
                {
                    cellGrid[x][y] = new Cell(false, x, y);
                    nextGenerationStatus[x][y] = false;

                }// end of for (int y = 0 ; y < maxYValue ; y++)
            }// end of for (int x = 0 ; x < maxXValue ; x ++ ) 
            contentPane.setVisible(false);
            contentPane.removeAll();
            print();
        }// end of methods actionPerformed(ActionEvent e)
    }// end of class Reset

    //ActionListener for the "slowDown" JMenuItem; slows down the time of each generation
    private class SlowDown implements ActionListener
    {
        public void actionPerformed (ActionEvent e )
        {
            if (intervalTime < 2000)
            {
                intervalTime = intervalTime + 100;
                timer.stop();
                timer.start();
            }
        }// end of methods actionPerformed(ActionEvent e)
    }// end of class SlowDown

    //ActionListener for the "speedUp" JMenuItem; speeds up the time of each generation
    private class SpeedUp implements ActionListener
    {
        public void actionPerformed (ActionEvent e)
        {
            if (intervalTime > 100 )
            {
                intervalTime = intervalTime - 100;
                timer.stop();
                timer.start();
            }// end of if (intervalTime > 100 )

        }// end of methods actionPerformed(ActionEvent e)
    }// end of class SpeedUp

    //ActionListener for the "startGame" JMenuItem; starts the game of life 
    private class StartGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            timer.start();
        }// end of methods actionPerformed(ActionEvent e)
    }// end of class StartGame

    //Stops the game of life; ActionListener for "stopGame" JMenuItem
    private class StopGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            timer.stop();
        }// end of methods actionPerformed(ActionEvent e)
    }// end of class StopGame

    //ActionListener for "timer" Timer; Starts the game as timer starts rolling
    private class TimerActionListener implements ActionListener 
    {
        public void actionPerformed (ActionEvent e)  
        {
            try
            {
                startGame();
            }// end of try 
            catch (InterruptedException f)
            {
            }// end of catch (InterruptedException f)
        }// end of method actionPerformed(ActionEvent e)
    }//end of class TimerActionListener
}// end of class GameOfLife
