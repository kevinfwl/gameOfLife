import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JWindow;

/**k
 * An object that would allow natural effects of cells (survival, resurrection, overpopulation, underpopulation) to take place on a grid
 * 
 * @author Nadim Sunderani (Roll #11)
 * @version 1.0 2015-04-20
 * @version 1.1 2015-04-21
 * @version 1.2 2015-04-22
 * @version 1.3 2015-04-24
 * @version 2.0 2015-04-25
 * @version 2.0.1 2015-04-27
 */
public class GUI_Implementation 
{
    /*
     * class fields
     * 
     */
    // the symbol to be outputted, to signify a dead cell
    private final String DEAD_CELL = "-";
    // a random generator, so as to assign random values to the first generation of cells
    private final Random GENERATOR = new Random();
    // the symbol to be outputted, to signify a live cell
    private final String LIVE_CELL = "*";
    private final long MILLISECONDS_PER_SECOND = 1000L;
    // a living cell will survive, if it has between 2 and 3 neighbouring cells, represented by between UNDERPOPULATION and OVERPOPULATION
    // a living cell will die, if it has more than 3 neighbouring cells, as if by overpopulation
    private final int OVERPOPULATION = 3;
    // a dead cell will resurrect, if it has exactly 3 neighbouring cells, as if by reproduction
    private final int RESURRECTION = 3;
    // a living cell will die, if it has less than 2 neighbouring cells, as if by underpopulation
    private final int UNDERPOPULATION = 2;

    /*
     * instance fields
     */ 
    // a 2-D array of cells, so as to have access to their current and future states, via their methods
    private JButton[][] buttons;
    private Cell[][] cell;
    private int delayBetweenGenerations;
    private int generation;
    private int numberOfCellsAlive;
    private int numberOfColumns = 10;
    private int numberOfNeighbours = 0;
    private int numberOfRows = 10;

    private JFrame imageOfCells = new JFrame("Cell Image");
    private Container imageContainerPane = imageOfCells.getContentPane();

    /**
     * Constructs a grid interface, that is able to show Conway's game of Life, by implementing <code>Cell</code> objects on a grid.
     */
    public GUI_Implementation()
    {
        createMenu();
    } // end of contructor Implementation(int lengthOfGrid, int heightOfGrid)

    /**
     * Creates a menu bar with menus and menu items allowing the choice of various options
     */
    public void createMenu()
    {
        // create a frame
        JFrame frame = new JFrame("");
        Container containerPane = frame.getContentPane();
        // containerPane.setLayout(new GridLayout(numberOfColumns, numberOfRows));

        // create a menu bar and add it to the frame
        JMenuBar menuBar = new JMenuBar();
        frame.add(menuBar);

        JMenu newGame = new JMenu("New Game");
        menuBar.add(newGame);

        JMenu options = new JMenu("Options");
        menuBar.add(options);

        JMenu help = new JMenu("Help");
        menuBar.add(help);

        JMenuItem automatic = new JMenuItem("Provide Specified Dimensions");
        newGame.add(automatic);
        automatic.addActionListener(new AutomaticListener());

        JMenuItem fiveByFive = new JMenuItem("New 5x5");
        newGame.add(fiveByFive);
        fiveByFive.addActionListener(new FiveByFiveListener());

        JMenuItem tenByTen = new JMenuItem("New 10x10");
        newGame.add(tenByTen);
        tenByTen.addActionListener(new TenByTenListener());

        JMenuItem quit = new JMenuItem("Quit Application");
        options.add(quit);
        quit.addActionListener(new QuitListener());

        JMenuItem howToPlay = new JMenuItem("How To Play");
        help.add(howToPlay);
        howToPlay.addActionListener(new HowToPlayListener());

        frame.pack();
        frame.setVisible(true);

        // imageContainerPane.setLayout(new GridLayout(numberOfColumns, numberOfRows));
    } // end of method createMenu()

    /**
     * Creates a representation of a game, in which a specified length and width of the grid are chosen, and where a delay length is chosen.
     * 
     * After constructing, randomizes the values of the cells for the first generation.
     * Displays a visual image of the grid of cells. 
     * Continues to change the state of each cell as the generations pass, updating the image; using the rules, applied through the constants: OVERPOPULATION, UNDERPOPULATION and RESURRECTION
     * 
     * @param lengthOfGrid the number of units on the x-axis of the grid; each unit will contain one cell; cannot be less than zero
     * @param heightOfGrid the number of units on the y-axis of the grid; each unit will contain one cell; cannot be less than zero
     * @param delay the delay, in seconds, between generations; must be positive
     */
    public void newAutomaticGame(int lengthOfGrid, int heightOfGrid, int delay) throws InterruptedException
    {
        if (lengthOfGrid > 0 && heightOfGrid > 0 && delay >= 0)
        {
            // create a grid with a specified size if greater than zero
            numberOfColumns = lengthOfGrid;
            numberOfRows = heightOfGrid;
            delayBetweenGenerations = delay;

            // sets the number of cells alive in the game to 0
            numberOfCellsAlive = 0;

            // allocates memory for the size of the array of Cells
            cell = new Cell[numberOfColumns][numberOfRows];

            // randomize the states of all the cells on the grid; for the first generation, each cell will either be alive (<code>true</code>) or dead (<code>false</code>)
            for (int y = 0; y < numberOfRows; y++)
            {
                for (int x = 0; x < numberOfColumns; x++)
                {
                    // create a new cell
                    cell[x][y] = new Cell(false);

                    // assign a random boolean value
                    cell[x][y].setCurrentState(GENERATOR.nextBoolean());

                    if (cell[x][y].getCurrentState()) // if the newly created cell is alive
                    {
                        numberOfCellsAlive += 1;
                    } // end of if (cell[x][y].getCurrentState())
                } // end of for (int x = 0; x < numberOfColumns; x++)
            } // end of for (int y = 0; y < numberOfRows; y++)

            // allocates memory for the size of the array of JButtons
            buttons = new JButton[numberOfColumns][numberOfRows];

            imageContainerPane.setLayout(new GridLayout(numberOfColumns, numberOfRows));
            createImageOfCells();
            // testIfAllCellsAreDead();

            generation = 1; 

            // while (numberOfCellsAlive > 0) // while there are still cells alive, continue game
            // {
            // Thread.sleep(10000);

            // checkNeighbours();
            // moveToNextGeneration();
            // imageContainerPane = imageOfCells.getContentPane();
            // imageContainerPane.removeAll();
            // imageOfCells.setVisible(false);                
            // imageOfCells = new JFrame();
            // imageContainerPane = imageOfCells.getContentPane();
            // imageContainerPane.setLayout(new GridLayout(numberOfColumns, numberOfRows));

            // createImageOfCells();
            // testIfAllCellsAreDead();
            //} // end of while (numberOfCellsAlive > 0)

            // System.out.println("You reached generation " + generation + " of the game");
        }
        else
        {
            // System.out.println("The length and width of a grid must be greater than zero; delay must be positive, try again!");
        } // end of if (lengthOfGrid > 0 && heightOfGrid > 0)

        generation = 0;
    } // end of method newAutomaticGame(int lengthOfGrid, int heightOfGrid, int delay) 

    /**
     * Checks each cell for the number of neighbouring cells it has, so as to be able to apply rules to game.
     */
    public void checkNeighbours() throws InterruptedException
    {
        for (int y = 0; y < numberOfRows; y++)
        {
            for (int x = 0; x < numberOfColumns; x++)
            {
                numberOfNeighbours = 0;

                /*
                 * Find out how many neighbours each cell has 
                 */

                if ((y-1) >= 0)
                {
                    if (cell[x][y-1].getCurrentState()) // up one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if (cell[x][y-1].getCurrentState())
                } // end of if ((y-1) >= 0)

                if ((y+1) < numberOfRows) // if such a row exists in the array
                {
                    if (cell[x][y+1].getCurrentState()) // down one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if (cell[x][y+1].getCurrentState())
                } // end of if ((y-1) <= numberOfRows)

                if ((x-1) >= 0) // if such a column exists
                {
                    if (cell[x-1][y].getCurrentState()) // left one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if (cell[x-1][y].getCurrentState())if (cell[x][y+1].getCurrentState())
                } // end of if ((x-1) >= 0)

                if ((x+1) < numberOfColumns)
                {
                    if (cell[x+1][y].getCurrentState()) // right one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if (cell[x][y+1].getCurrentState())
                } // end of if ((x+1) < numberOfColumns)

                if ((x-1) >= 0 && (y-1) >= 0)
                {
                    if (cell[x-1][y-1].getCurrentState()) // up-left one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if cell[x-1][y-1].getCurrentState())
                } // end of if ((x-1) >= 0 && (y-1) >= 0)

                if ((x-1) >= 0 && (y+1) < numberOfRows)           
                {
                    if (cell[x-1][y+1].getCurrentState()) // down-left one cell
                    {
                        numberOfNeighbours += 1; 
                    } // end of if (cell[x-1][y+1].getCurrentState())
                } // end of if ((x-1) >= 0 && (y+1) < numberOfRows)   

                if ((x+1) < numberOfColumns && (y-1) >= 0)           
                {
                    if (cell[x+1][y-1].getCurrentState()) // up-right one cell
                    {
                        numberOfNeighbours += 1;
                    } // end of if (cell[x+1][y-1].getCurrentState())
                } // end of if ((x+1) < numberOfColumns && (y-1) >= 0)  

                if ((x+1) < numberOfColumns && (y+1) < numberOfRows)           
                {
                    if (cell[x+1][y+1].getCurrentState()) // down-right one cell
                    {
                        numberOfNeighbours += 1;
                    } // end of if (cell[x+1][y+1].getCurrentState())
                } // end of if ((x+1) < numberOfColumns && (y-1) < numberOfRows)

                /*
                 * Based on rules of the game, assign the next state of each cell, to the <code>nextState</code> instance field
                 */

                if (cell[x][y].getCurrentState()) // if cell is currently alive
                {
                    if (numberOfNeighbours < UNDERPOPULATION) // if cell has less than 2 neighbours
                    {
                        cell[x][y].setNextState(false);
                    } 
                    else if (numberOfNeighbours >= UNDERPOPULATION && numberOfNeighbours <= OVERPOPULATION) // if cell has 2 or 3 neighbours
                    {
                        cell[x][y].setNextState(true);
                    }
                    else if (numberOfNeighbours < OVERPOPULATION) // if cell has more than 3 neighbours
                    {
                        cell[x][y].setNextState(false);
                    } // end of if if (numberOfNeighbours < UNDERPOPULATION)
                }
                else if (!cell[x][y].getCurrentState()) // if cell is currently dead
                {
                    if (numberOfNeighbours == RESURRECTION) // if a dead cell has 3 neighbours
                    {
                        cell[x][y].setNextState(true);
                    } // end of if (numberOfNeighbours = RESURRECTION)
                } // end of if (cell[x][y].getCurrentState())  
            }  // end of for (int x = 0; x < numberOfColumns; x++)
        } // end of for (int y = 0; y < numberOfRows; y++)
    } // end of method checkNeighbours()

    /**
     * Creates an image in the console, of the grid of cells.
     */
    public void createImageOfCells() throws InterruptedException    
    {      
        if (imageContainerPane.getComponentCount() > 0)
        {
            for (int y = 0; y < numberOfRows; y++)
            {
                for (int x = 0; x < numberOfColumns; x++)
                {
                    imageContainerPane.remove(buttons[x][y]);
                }
            }
        }
        
        for (int y = 0; y < numberOfRows; y++)
        {
            for (int x = 0; x < numberOfColumns; x++)
            {
                if (cell[x][y].getCurrentState())
                {
                    // output the symbol for a live cell
                    buttons[x][y] = new JButton(LIVE_CELL);
                    // buttons[x][y].setVisible(true);
                    imageContainerPane.add(buttons[x][y]);
                    
                    // System.out.print("*");
                }
                else if (!cell[x][y].getCurrentState())
                {
                    // output the symbol for a dead cell
                    buttons[x][y] = new JButton(DEAD_CELL);
                    imageContainerPane.add(buttons[x][y]);
                    // buttons[x][y].setVisible(true);
                    // System.out.print("-");
                }
            } // end of for (int x = 0; x < numberOfColumns; x++)

            // System.out.println("");

        } // end of for (int y = 0; y < numberOfRows; y++)

        imageOfCells.pack();
        imageOfCells.setVisible(true);    

        System.out.println("Generation " + generation);

        if (generation >= 50)
        {
            imageOfCells.setVisible(false);
        }

        // Thread.sleep(2000);

        /*if (numberOfCellsAlive > 0)
        {
        System.out.println("\fYou are in generation " + generation);
        Thread.sleep(MILLISECONDS_PER_SECOND * delayBetweenGenerations);
        }*/

        /*for (int delayToCloseWindow = 5 * delayBetweenGenerations; delayToCloseWindow >= 0; delayToCloseWindow--)
        {                
        System.out.println("\f");
        // imageOfCells.removeAll();
        // createImageOfCells();
        System.out.println("Game over... you reached generation " + generation + " but have no cells left");
        System.out.println("Window will close in " + delayToCloseWindow + " seconds");
        Thread.sleep(MILLISECONDS_PER_SECOND);*/

        //}

        // } // end of if (numberOfCellsAlive == 0)

    } // end of method createImageOfCells()

    /**
     * Applies rules of game to carry out the future state of each cell, such that it becomes its current state.
     */
    public void moveToNextGeneration() throws InterruptedException
    {
        for (int y = 0; y < numberOfRows; y++)
        {
            for (int x = 0; x < numberOfColumns; x++)
            {
                if (!cell[x][y].getCurrentState() && cell[x][y].getNextState()) // if the cell becomes alive in the next generation
                {
                    numberOfCellsAlive += 1;
                    cell[x][y].setCurrentState(true);
                    cell[x][y].setNextState(false);
                }
                else if (cell[x][y].getCurrentState() && cell[x][y].getNextState()) // if the cell remains alive in the next generation
                {
                    cell[x][y].setCurrentState(true);
                    cell[x][y].setNextState(false);
                }
                else if (cell[x][y].getCurrentState() && !cell[x][y].getNextState()) // if the cell dies for the next generation
                {
                    numberOfCellsAlive -= 1;
                    cell[x][y].setCurrentState(false);
                    cell[x][y].setNextState(false);
                }
            } // end of for (int x = 0; x < numberOfColumns; x++)
        } // end of for (int y = 0; y < numberOfRows; y++)

        generation += 1;

        // createImageOfCells();
        // testIfAllCellsAreDead();
    } // end of method moveToNextGeneration()

    private void quitGame()
    {
        imageOfCells.setVisible(false);
    } // end of method quitProgram()

    /**
     * Tests whether all cells are dead. If there are still cells remaining, the game continues. Otherwise, the terminal window is closed after a delay.
     */
    public void testIfAllCellsAreDead() throws InterruptedException
    {
        if (numberOfCellsAlive == 0)
        {
            for (int delayToCloseWindow = 5 * delayBetweenGenerations; delayToCloseWindow >= 0; delayToCloseWindow--)
            {                
                System.out.println("\f");
                imageOfCells.removeAll();
                createImageOfCells();
                System.out.println("Game over... you reached generation " + generation + " but have no cells left");
                System.out.println("Window will close in " + delayToCloseWindow + " seconds");
                Thread.sleep(MILLISECONDS_PER_SECOND);
            } // end of for (int delayToCloseWindow = delayBetweenGenerations; delayToCloseWindow >= 0; delayToCloseWindow--)
            // quitGame();
        }
        else if (numberOfCellsAlive > 0)
        {
            System.out.println("You are in generation " + generation);
            Thread.sleep(MILLISECONDS_PER_SECOND * delayBetweenGenerations);
            System.out.println("\f");
        } // end of if (numberOfCellsAlive == 0)
    } // end of method testIfAllCellsAreDead()

    class AutomaticListener implements ActionListener
    {
        /*
         * Listens for the "Provide Specified Dimensions" button to be clicked
         * 
         * @param event the button that was clicked
         */
        public void actionPerformed(ActionEvent event)
        {    
            // user input for new game

            // clear GUI log of buttons
            if (imageContainerPane.getComponentCount() > 0)
            {
                for (int y = 0; y < numberOfRows; y++)
                {
                    for (int x = 0; x < numberOfColumns; x++)
                    {
                        imageContainerPane.remove(buttons[x][y]);
                    }
                }
            }

            try
            {
                newGame(3, 3, 1);
            }
            catch (InterruptedException exception)
            {
                System.out.println("Error Automatic");
            }

        } // end of method actionPerformed(ActionEvent event
        
        private void newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations) throws InterruptedException
        {
            newAutomaticGame(lengthOfGrid, heightOfGrid, delayBetweenGenerations);
            while (numberOfCellsAlive > 0) // while there are still cells alive, continue game 
            {
                Thread.sleep(1000);

                checkNeighbours();
                moveToNextGeneration();
                // imageContainerPane = imageOfCells.getContentPane();
                // imageContainerPane.removeAll();
                // imageOfCells.setVisible(false);                
                // imageOfCells = new JFrame();
                // imageContainerPane = imageOfCells.getContentPane();
                // imageContainerPane.setLayout(new GridLayout(numberOfColumns, numberOfRows));

                createImageOfCells();
                // testIfAllCellsAreDead();
            } // end of while (numberOfCellsAlive > 0)
            System.out.println("Success! Automatic");
        } // end of method newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations)
    } // end of class ActionListener

    class FiveByFiveListener implements ActionListener
    {
        /*
         * Listens for the "Five By Five" button to be clicked
         * 
         * @param event the button that was clicked
         */
        public void actionPerformed(ActionEvent event) 
        {    
            // clear GUI log of buttons
            if (imageContainerPane.getComponentCount() > 0)
            {
                for (int y = 0; y < numberOfRows; y++)
                {
                    for (int x = 0; x < numberOfColumns; x++)
                    {
                        imageContainerPane.remove(buttons[x][y]);
                    }
                }
            }

            try
            {
                newGame(5, 5, 1);
            }
            catch(InterruptedException exception)
            {
                System.out.println("Error 5x5");
            }
        } // end of method actionPerformed(ActionEvent event

        private void newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations) throws InterruptedException
        {
            newAutomaticGame(lengthOfGrid, heightOfGrid, delayBetweenGenerations);
            System.out.println("Success! 5x5");
        } // end of method newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations)
    } // end of class FiveByFiveListener

    class TenByTenListener implements ActionListener
    {
        /*
         * Listens for the "Ten by Ten" button to be clicked
         * 
         * @param event the button that was clicked
         */
        public void actionPerformed(ActionEvent event) 
        {
            // clear GUI log of buttons
            if (imageContainerPane.getComponentCount() > 0)
            {
                for (int y = 0; y < numberOfRows; y++)
                {
                    for (int x = 0; x < numberOfColumns; x++)
                    {
                        imageContainerPane.remove(buttons[x][y]);
                    }
                }
            }

            try
            {
                newGame(10, 10, 1);
            }
            catch(InterruptedException exception)
            {
                System.out.print("Error 10x10");
            }
        } // end of method actionPerformed(ActionEvent event)

        private void newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations) throws InterruptedException
        {
            newAutomaticGame(lengthOfGrid, heightOfGrid, delayBetweenGenerations);
            System.out.println("Success! 10x10");
        } // end of method newGame(int lengthOfGrid, int heightOfGrid, int delayBetweenGenerations)
    } // end of class TenByTenListener

    class HowToPlayListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) 
        {
            JFrame helpWindow = new JFrame("Help Window");
            Container containerPane = helpWindow.getContentPane();
            containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));

            JTextField title = new JTextField("Rules of Game");
            containerPane.add(title);

            JTextField rule1 = new JTextField("1. The first generation of cells are randomly provided to you");
            containerPane.add(rule1);

            JTextField rule2 = new JTextField("2. If a living cell has 2 or 3 neighbours, it survives to the next generation");
            containerPane.add(rule2);

            JTextField rule3 = new JTextField("3. If a living cell has less than 2 neighbours, it dies, as if by underpopulation");
            containerPane.add(rule3);

            JTextField rule4 = new JTextField("4. If a living cell has more than 3 neighbours, it dies, as if by overpopulation");
            containerPane.add(rule4);

            JTextField rule5 = new JTextField("5. If a dead cell has exactly 3 neighbours, it resurrects, as if by reprodution");
            containerPane.add(rule5);

            helpWindow.pack();
            helpWindow.setVisible(true);         
        } // end of method actionPerformed(ActionEvent event)
    } // end of class HowToPlayListener

    class QuitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            System.exit(0);
        } // end of method actionPerformed(ActionEvent event)
    } // end of class QuitListener
} // end of class GUI_Implementation