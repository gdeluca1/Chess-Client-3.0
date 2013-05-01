/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class is used to translate the pieceList to a string and vice versa.
 * @author Gennaro
 */
public abstract class IO {
    
    /**
     * Serializes the pieceList for transmitting over a socket connection.
     * Previous square (x, y)
     * Square (x, y)
     * has moved
     */
    public static String serialize()
    {
        String toReturn = "";
        
        CopyOnWriteArrayList<ChessPiece> pieceList = ChessPieceBox.getInstance().getPieceList();
        
        for(ChessPiece piece : pieceList)
        {
            if(piece.getPreviousSquare().getXPos() < 0 || piece.getPreviousSquare().getXPos() > 7 )
            {
                toReturn += piece.getPreviousSquare().getXPos() + "," + piece.getPreviousSquare().getYPos() + ",";
            }
            else
            {
                toReturn += (7-piece.getPreviousSquare().getXPos()) + "," + (7-piece.getPreviousSquare().getYPos()) + ",";
            }
            
            if(piece.getSquare().getXPos() < 0 || piece.getSquare().getXPos() > 7 )
            {
                toReturn += piece.getSquare().getXPos() + "," + piece.getSquare().getYPos() + "," + piece.hasMoved();
            }
            else
            {
                toReturn += (7-piece.getSquare().getXPos()) + "," + (7-piece.getSquare().getYPos()) + "," + piece.hasMoved();
            }
            toReturn += "," + piece.getPieceURL();
            toReturn += "/";
            
            piece.resetPreviousSquare();
        }
        
        return toReturn + "\n";
    }
    
    /**
     * Deserializes a string that was transmitted over a socket connection.
     * Previous square (x, y)
     * Square (x, y)
     * has moved
     */
    public static void deserialize(String s) throws IOException
    {
        String[] completeArray = s.split("/");
        
        ArrayList<String> stringList = new ArrayList<>();
        for(String substring : completeArray)
        {
            String[] pieceArray = substring.split(",");
            int oldX = Integer.parseInt(pieceArray[0]);
            int oldY = Integer.parseInt(pieceArray[1]);
            int newX = Integer.parseInt(pieceArray[2]);
            int newY = Integer.parseInt(pieceArray[3]);
            boolean hasMoved = Boolean.parseBoolean(pieceArray[4]);
            if(newX == 8)
            {
                ChessPieceBox.getInstance().movePiece(oldX, oldY, newX, newY, hasMoved, pieceArray[5]);
            }
            else
            {
                stringList.add(substring);
            }
        }
        ChessSquareBox.getInstance().resetSquareColors();
        
        for(String substring : stringList)
        {
            String[] pieceArray = substring.split(",");
            int oldX = Integer.parseInt(pieceArray[0]);
            int oldY = Integer.parseInt(pieceArray[1]);
            int newX = Integer.parseInt(pieceArray[2]);
            int newY = Integer.parseInt(pieceArray[3]);
            boolean hasMoved = Boolean.parseBoolean(pieceArray[4]);
            String pieceURL = pieceArray[5];
            
            ChessPieceBox.getInstance().movePiece(oldX, oldY, newX, newY, hasMoved, pieceURL);
        }
        ChessPieceBox.getInstance().resetPreviousSquares();
    }
}
