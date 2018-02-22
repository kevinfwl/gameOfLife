import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Write a description of class Cell here.
 * 
 * @author Kevin Feng role number: 5 
 * @version 1.0 
 */
public class Cell implements ActionListener
{
    private boolean cellStatus;
    private int xCoordinate;
    private int yCoordinate;
    private JButton button;

    final Color ALIVE_COLOUR = Color.GREEN;
    final Color DEAD_COLOUR = Color.WHITE;

    // Constructor

    /**
     * Creates a <code>Cell<code> with a default status, x coordinate and y coordinate
     */
    public Cell ()
    {
        cellStatus = false;
        xCoordinate = 0;
        yCoordinate = 0;
        button = new JButton();
    }// end of constructor Cell()

    /**
     * Creates a <code>Cell<code> with a specified status, x coordinate and y coordinate
     * 
     * @param cellStatus true when cell is alive, false when cell is dead
     * @param xCoordinate the location of the cell in x coordinates
     * @param yCoordinate the location of the cell in y coordinates
     */
    public Cell(boolean cellStatus, int xCoordinate, int yCoordinate)
    {
        this.cellStatus = cellStatus;

        if (xCoordinate >= 0)
        {
            this.xCoordinate = xCoordinate;
        }
        else 
        {
            this.xCoordinate = 0;
        }// end of if (xCoordinate >= 0)

        if (yCoordinate >= 0)
        {
            this.yCoordinate = yCoordinate;
        }
        else 
        {
            this.yCoordinate = 0;
        } // end of if (yCoordinate >= 0)

        button = new JButton();
    }// end of constructor Cell(boolean cellStatus, int xCoordinate, int yCoordinate)

    /**
     * Returns the status of this cell
     * 
     * @return the status of this cell, true if alive, false if dead
     */
    public boolean isAlive ()
    {
        return cellStatus;
    }// end of accessor isAlive ()

    /**
     *Returns the x coordinates of this cell
     *
     *@return x coordinate of this cell in integers
     */
    public int getXCoordinate ()
    {
        return xCoordinate;
    }// end of accessor getXCoordinate ()

    /**
     *Returns the y coordinates of this cell
     *
     *@return y coordinate of this cell in integers
     */
    public int getYCoordinate ()
    {
        return yCoordinate;
    }// end of accessor getYCoordinate ()

    /**
     *Sets the status of this cell
     *
     *@param cellStatus true if the cell is wanted to be set alive, false if the cell is wanted to be set dead
     */
    public void setCellStatus(boolean cellStatus)
    {
        this.cellStatus = cellStatus;
    }// end of mutator setCellStatus(boolean cellStatus)

    /**
     *Sets the x coordinate of this cell
     *
     *@param xCoordinate x coordinate of this cell in integer value
     */
    public void setXCoordinate(int xCoordinate)
    {
        if (xCoordinate >= 0)
        {
            this.xCoordinate = xCoordinate;
        }
        else 
        {
            this.xCoordinate = 0;
        }// end of if (xCoordinate >= 0)
    }// end of mutator setXCoordinate(int xCoordinate)

    /**
     *Sets the y coordinate of this cell
     *
     *@param yCoordinate y coordinate of this cell in integer value
     */
    public void setYCoordinate (int yCoordinate)
    {
        if (yCoordinate >= 0)
        {
            this.yCoordinate = xCoordinate;
        }
        else 
        {
            this.yCoordinate = 0;
        }// end of if (yCoordinate >= 0)
    }// end of mutator setYCoordinate (int yCoordinate)

    /**
     * Returns a symbol according the the status of this cell
     * 
     * @return a black box if this cell is alive, an empty box if this cell is dead
     */
    public char drawCell ()
    {
        final char ALIVE_SYMBOL = '\u25A0';
        final char DEAD_SYMBOL = '\u25A1';
        if (cellStatus)
        {
            return ALIVE_SYMBOL;
        }
        else
        {
            return DEAD_SYMBOL;
        }// end of if (cellStatus)
    }// end of method drawCell ()

    public JButton drawButton ()
    {

        if (isAlive())
        {
            button.setBackground(ALIVE_COLOUR);
        }
        else 
        {
            button.setBackground(DEAD_COLOUR);
        }

        button.addActionListener(this);
        return button;
    }

    public void actionPerformed (ActionEvent e)
    {
        if (isAlive())
        {
            cellStatus = false;
            button.setBackground(DEAD_COLOUR);
        }
        else
        {
            cellStatus = true;
            button.setBackground(ALIVE_COLOUR);
        }
    }// end of public class Cell
}
