/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chessmaps.ColorMap;
import java.util.ArrayList;

/**
 *
 * @author Gennaro
 */
public class ChessSquareBox
{
    private static ChessSquareBox chessSquareBox;
    private ArrayList<ChessSquare> squareList;
    private ChessBoard chessBoard;
    
    protected ChessSquareBox()
    {
        chessBoard = Chess.getChessBoard();
        
        squareList = new ArrayList<>();
        
        // Fill the list of squares and assign colors to each square.
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                ChessSquare square = new ChessSquare(i, j);
                
                if(i%2 == 0 && j%2 == 0)
                {
                    square.setColor(ColorMap.WHITE);
                }
                else if(i%2 != 0 && j%2 != 0)
                {
                    square.setColor(ColorMap.WHITE);
                }
                else
                {
                    square.setColor(ColorMap.BLACK);
                }
                squareList.add(square);
            }
        }
    }
    
    /**
     * Get the box of chess squares.
     */
    public static ChessSquareBox getInstance()
    {
       if(chessSquareBox == null)
       {
           chessSquareBox = new ChessSquareBox();
       }
       
       return chessSquareBox;
    }
    
    /**
     * Find a Chess Square based on an xPos and a yPos.
     * @param xPos The x position, between 0 and 7 inclusive.
     * @param yPos The y position, between 0 and 7 inclusive.
     * @return 
     */
    public ChessSquare findSquare(int xPos, int yPos)
    {
        if(xPos > 7 || yPos > 7)
        {
            throw new IllegalArgumentException("The x and y positions must be between 0 and 7 inclusive.");
        }
        
        ChessSquare toReturn = null;
        
        // Optimize: searching algorithm (may need comparable usage)
        for(ChessSquare square : squareList)
        {
            if(square.getXPos() == xPos && square.getYPos() == yPos)
            {
                toReturn = square;
            }
        }
        return toReturn;
    }
    
    public ChessSquare findSquareFromPixels(int xPos, int yPos)
    {
        for(ChessSquare square : squareList)
        {
            if((xPos*8/chessBoard.getPanelWidth() == square.getXPos()) &&
                        (yPos*8/chessBoard.getPanelHeight() == square.getYPos()))
            {
                return square;
            }
        }
        return null;
    }
    
    /**
     * Resets the colors of all of the squares.
     */
    public void resetSquareColors()
    {
        for(ChessSquare square : squareList)
        {
            square.resetColor();
        }
        Chess.getChessBoard().repaint();
    }

    public ArrayList<ChessSquare> getSquareList()
    {
        return squareList;
    }
}
