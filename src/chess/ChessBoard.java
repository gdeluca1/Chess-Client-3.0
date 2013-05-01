/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chessmaps.ChessPlayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author Gennaro
 */
public class ChessBoard extends JPanel
{
    
    private ChessPlayer player;
    
    private int panelWidth = -1;
    private int panelHeight = -1;
    
    private final static boolean DEBUG = true;
    
    public static boolean adminModeEnabled = false;
    private static boolean firstTime = true;
    
    
    /**
     * 
     * @param player an enumerated variable representing different colors (black or white)
     */
    public ChessBoard(ChessPlayer player)
    {
        this.player = player;
        ChessPieceBox.prepareInstance(player);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GRAY);
        
        panelHeight = getHeight();
        panelWidth = getWidth();
        
        // Color all the squares.
        for(ChessSquare square : ChessSquareBox.getInstance().getSquareList())
        {
            g2d.setColor(square.getColor());
            g2d.fillRect(square.getXPos() * panelWidth/8, square.getYPos() * panelHeight/8, panelWidth/8, panelHeight/8);
        }
       
        // Draw the pieces.
        CopyOnWriteArrayList<ChessPiece> pieceList = ChessPieceBox.getInstance().getPieceList();
        
        for(ChessPiece piece : pieceList)
        {
            g2d.drawImage(piece.getImage(), piece.getSquare().getXPos() * panelWidth/8, piece.getSquare().getYPos() * panelHeight/8, this);
            //piece.draw(g2d, panelWidth, panelHeight, this);
        }
    }
    
    public void resizePieces()
    {
        panelWidth = getWidth();
        panelHeight = getHeight();
        SwingWorker worker = new SwingWorker<Void, Void>()
        {
            @Override
            public Void doInBackground()
            {
                for(ChessPiece piece: ChessPieceBox.getInstance().getPieceList())
                {
                    if(panelWidth > 0 && panelHeight > 0) 
                    {
                        piece.resetPieceImage();
                        piece.scale(panelWidth, panelHeight);
                    }
                }
                repaint();
                return null;
            }
            
            @Override
            public void done()
            {
            }
        };
        worker.execute();
        
     }
    
    public int getPanelWidth()
    {
        return panelWidth;
    }
    
    public int getPanelHeight()
    {
        return panelHeight;
    }

    public ChessPlayer getPlayer()
    {
        return player;
    }
    
    public void setDimensions(int panelWidth, int panelHeight)
    {
            this.panelWidth = panelWidth;
            this.panelHeight = panelHeight;
    }
}
