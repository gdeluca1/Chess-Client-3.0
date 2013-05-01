/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chessmaps.ColorMap;
import chessmaps.PieceMap;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Gennaro
 */
public class ChessPiece
{
    private Image piece;
    private ChessSquare square;
    private ChessSquare previousSquare;
    
    private String pieceURL;
    
    private int pieceType;
    private boolean pieceColor;
    
    private boolean hasMoved = false;
    
    private ChessSquareBox chessSquareBox;
    
    /**
     * Piece type is an integer as defined in the ChessMove.java file.
     * @param pieceURL
     * @param xPos
     * @param yPos
     * @param pieceType
     * @throws IOException 
     */
    public ChessPiece(String pieceURL, int xPos, int yPos, int pieceType, boolean pieceColor)
    {
        chessSquareBox = ChessSquareBox.getInstance();
        try
        {
            piece = ImageIO.read(getClass().getResource("/pieces/" + pieceURL + ".png"));
        } catch (IOException ex)
        {
            Console.err.println("Invalid image selected.");
            Logger.getLogger(ChessPiece.class.getName()).log(Level.SEVERE, null, ex);
        }
        square = chessSquareBox.findSquare(xPos, yPos);
        previousSquare = square;
        this.pieceURL = pieceURL;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }
    
    /**
     * Puts the piece on square 8,8.
     */
    public void remove()
    {
        previousSquare = square;
        square = new ChessSquare(8, 8);
    }
    
    /**
     * Resets the piece's image based on its current piece URL.
     */
    public void resetPieceImage()
    {
        try {
            piece = ImageIO.read(getClass().getResource("/pieces/" + pieceURL + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(ChessPiece.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changePiece(int panelWidth, int panelHeight, String newPieceType)
    {
        pieceURL = Chess.getPlayerColor() + newPieceType;
        try {
            piece = ImageIO.read(getClass().getResource("/pieces/" + pieceURL + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(ChessPiece.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch(newPieceType){
                case "queen":
                    pieceType = PieceMap.QUEEN;
                    break;
                case "rook":
                    pieceType = PieceMap.ROOK;
                    break;
                case "knight":
                    pieceType = PieceMap.KNIGHT;
                    break;
                case "bishop":
                    pieceType = PieceMap.BISHOP;
                    break;
                default:
                    Console.err.println("Invalid piece selection.");
        }
        scale(panelWidth, panelHeight);
        //throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString()
    {
        return "Piece: " + pieceURL;
    }
    
    /**
     * Return the square the piece is on.
     * @return 
     */
    public ChessSquare getSquare()
    {
        return square;
    }
    
    public ChessSquare getPreviousSquare()
    {
        return previousSquare;
    }
    
    public void resetPreviousSquare()
    {
        previousSquare = square;
    }
    
    /**
     * This method also edits the colors of the squares.
     */
    public void setSquare(int newX, int newY)
    {
        boolean same;
        boolean removed;
        if(newX == square.getXPos() && newY == square.getYPos())
        {
            same = true;
        }
        else
        {
            same = false;
        }
        removed = (newX > 7 || newX < 0) ? true : false;
        if(!same)
        {
            if(!removed)
            {
                square.setColor(ColorMap.FROM);
            }
            previousSquare = square;
            if(!removed)
            {
                square = chessSquareBox.findSquare(newX, newY);
                square.setColor(ColorMap.TO);
            }
            else
            {
                System.out.println("Removed.");
                square = new ChessSquare(newX, newY);
            }
            Chess.getChessBoard().repaint();
        }
    }
    
    public void scale(int panelWidth, int panelHeight)
    {
         setImage(getImage().getScaledInstance(panelWidth/8, panelHeight/8, Image.SCALE_SMOOTH));
    }
    
    public Image getImage()
    {
        return piece;
    }
    
    public void setImage(Image piece)
    {
        this.piece = piece;
    }
    
    public int getWidth(ImageObserver observer) {
        return piece.getWidth(observer);
    }

    public int getHeight(ImageObserver observer) {
        return piece.getHeight(observer);
    }

    public ImageProducer getSource() {
        return piece.getSource();
    }

    public boolean getPieceColor()
    {
        return pieceColor;
    }

    public int getPieceType()
    {
        return pieceType;
    }
    
    public boolean hasMoved()
    {
        return hasMoved;
    }
    
    public void setMoved()
    {
        hasMoved = true;
    }
    
    public Graphics getGraphics() {
        return piece.getGraphics();
    }

    public Object getProperty(String name, ImageObserver observer) {
        return piece.getProperty(name, observer);
    }

    public String getPieceURL()
    {
        return pieceURL;
    }

    public void setPieceURL(String pieceURL)
    {
        this.pieceURL = pieceURL;
        resetPieceImage();
    }
}
