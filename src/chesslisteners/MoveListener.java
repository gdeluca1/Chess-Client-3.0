/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chesslisteners;

import chess.Chess;
import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPieceBox;
import chess.ChessPieceChooser;
import chessmaps.ChessPlayer;
import chess.ChessSquare;
import chess.ChessSquareBox;
import chessmaps.ColorMap;
import chess.Console;
import chess.IO;
import chessmaps.PieceMap;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import messaging.InputThread;

    

/**
 *
 * @author Gennaro
 */
public class MoveListener implements MouseListener
    {
        private ChessPiece selectedPiece;
        
        private ChessPieceBox chessPieceBox;
        private ChessSquareBox chessSquareBox;
        
        /**
        * For connections.
        */
       static String IP_ADDRESS;
       final static int PORT = 6789;
       Socket socket = null;
       
       private static boolean yourTurn;
       private boolean firstTime = true;

       public MoveListener()
       {
            IP_ADDRESS = JOptionPane.showInputDialog("IP Address?");
            
            while(socket == null)
            {
               try
               {
                   socket = new Socket(IP_ADDRESS, PORT);
               } 
               catch (UnknownHostException ex)
               {
                   Console.err.println("UnknownHostException.");
                   Logger.getLogger(MoveListener.class.getName()).log(Level.SEVERE, null, ex);
               } 
               catch (IOException ex)
               {
                   Console.out.println("IOException from trying to connect.");
                   Logger.getLogger(MoveListener.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            
            if(socket != null)
            {
                Console.admin.println("Welcome!");
            }
            
            chessPieceBox = ChessPieceBox.getInstance();
            chessSquareBox = ChessSquareBox.getInstance();
            
            yourTurn = (chessPieceBox.getPlayer() == ChessPlayer.white) ? true : false;
       }
       
       public void startThread()
       {
            new Thread()
            {
                @Override
                public void start()
                {
                    if(firstTime)
                    {
                        firstTime = false;
                        try
                        {
                            InputStreamReader newisr = new InputStreamReader(socket.getInputStream());
                            BufferedReader reader = new BufferedReader(newisr);

                            String input = reader.readLine();
                            IO.deserialize(input);

                            yourTurn = true;
                        }
                        catch(IOException ex)
                        {
                            ex.printStackTrace();
                        }  
                    }
                }     
            }.start();
       }
       
        @Override
        public void mousePressed(MouseEvent e)
        {
            Chess.getGMenuBar().getMenuBar().requestFocus();
            
            if(!InputThread.isOpen())
            {
                e.consume();
                return;
            }
            
            if(yourTurn) 
            {
                // Case: a square was just pressed with no piece selected.
                if(selectedPiece == null)
                {
                    // A piece is on that square with the correct color.
                    selectedPiece = chessPieceBox.getPieceFromPixels(e.getX(), e.getY());
                    
                    if(selectedPiece != null)   
                    {   
                        if(((chessPieceBox.getPlayer() == ChessPlayer.white && selectedPiece.getPieceColor()) == PieceMap.WHITE) || 
                            (chessPieceBox.getPlayer() ==  ChessPlayer.black && selectedPiece.getPieceColor() == PieceMap.BLACK)) 
                        {
                            selectedPiece.getSquare().setColor(ColorMap.SELECTED);
                            Chess.getChessBoard().repaint();
                        }
                        
                        else
                        {
                            selectedPiece = null;
                        }
                    }
                }
                // The square with the same piece on it was pressed
                else if(chessSquareBox.findSquareFromPixels(e.getX(), e.getY()) == selectedPiece.getSquare())
                {
                    selectedPiece.getSquare().resetColor();
                    selectedPiece = null;
                    Chess.getChessBoard().repaint();
                }
                // A different square was pressed with a piece selected.
                else
                {
                    
                    int newX = e.getX()*8/Chess.getChessBoard().getPanelWidth();
                    int newY = e.getY()*8/Chess.getChessBoard().getPanelHeight();
                    
                    boolean isCastling;
                    
                    // TODO: admin mode
                    // Check to see if it can actually move.
              /*      if(Chess.getChessBoard().adminModeEnabled)
                    {
                        if(getPieceFromSquare(newX, newY) != null)
                        {
                            getPieceFromSquare(newX, newY).remove();
                        }
                        selectedPiece.getSquare().setX(e.getX()*8/(panelWidth));
                        selectedPiece.getSquare().setY(e.getY()*8/(panelHeight));
                        containingSquare.setColor(containingSquare.originalColor);
                        selectedPiece.hasMoved = true;
                        selectedPiece = null;
                        repaint();
                    } */
                    if((isCastling = ChessMove.canCastle(selectedPiece, chessSquareBox.findSquare(newX, newY))) || 
                            ChessMove.canMove(selectedPiece, chessSquareBox.findSquare(newX, newY)))
                    {
                        chessSquareBox.resetSquareColors();
                        
                        // Handle castle.
                        if(isCastling)
                        {
                            ChessSquare targetSquare = chessSquareBox.findSquare(newX, newY);
                            ChessPiece rook;
                            switch(ChessMove.castleSide) {
                                case ChessMove.KING_SIDE:
                                    rook = chessPieceBox.getPieceFromSquare(targetSquare.getXPos() + (targetSquare.getXPos() - selectedPiece.getSquare().getXPos())/2, 
                                            targetSquare.getYPos());
                                    rook.setSquare(targetSquare.getXPos() - (targetSquare.getXPos() - selectedPiece.getSquare().getXPos())/2 , rook.getSquare().getYPos());
                                    break;
                                case ChessMove.QUEEN_SIDE:
                                    rook = chessPieceBox.getPieceFromSquare(targetSquare.getXPos() + (targetSquare.getXPos() - selectedPiece.getSquare().getXPos()), 
                                            targetSquare.getYPos());
                                    rook.setSquare(targetSquare.getXPos() - (targetSquare.getXPos() - selectedPiece.getSquare().getXPos())/2 , rook.getSquare().getYPos());
                                    break;
                            }
                        }
                        
                        // If there's a piece that you're attacking, remove it.
                        if(chessPieceBox.getPieceFromSquare(newX, newY) != null)
                        {
                            chessPieceBox.getPieceFromSquare(newX, newY).remove();
                        }
                        selectedPiece.setSquare(e.getX()*8/Chess.getChessBoard().getPanelWidth(), e.getY()*8/Chess.getChessBoard().getPanelHeight());
                        
                        // Handle a pawn reaching the end of the board here.
                        if(selectedPiece.getPieceType() == PieceMap.PAWN && (selectedPiece.getSquare().getYPos() == 0 || selectedPiece.getSquare().getYPos() == 7))
                        {
                            ChessPieceChooser pieceChooser = new ChessPieceChooser(null, true);
                            pieceChooser.setVisible(true);
                            while(pieceChooser.isVisible())
                            {
                                // Wait.
                            }
                            selectedPiece.changePiece(Chess.getChessBoard().getPanelWidth(), Chess.getChessBoard().getPanelHeight(), chessPieceBox.getSelectedPiece());
                        }
                        
                        selectedPiece.setMoved();
                        selectedPiece = null;
                        Chess.getChessBoard().repaint();
                        
                        // Handle socket data transfer here.
                        
                        new Thread() {
                            
                            @Override
                            public void run()
                            {
                                while(socket.isClosed())
                                {
                                    try {
                                        socket = new Socket(IP_ADDRESS, PORT);
                                    } catch (IOException ex) {
                                        Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                 if(socket != null && !socket.isClosed())
                                    {
                                    DataOutputStream out;
                                    try {
                                        out = new DataOutputStream(socket.getOutputStream());
                                        
                                        out.writeBytes(IO.serialize());
                                        
                                        out.flush();
                                        
                                        yourTurn = false;
                                        
                                        InputStreamReader newisr = new InputStreamReader(socket.getInputStream());
                                        BufferedReader reader = new BufferedReader(newisr);
                                        
                                        String input = reader.readLine();
                                        IO.deserialize(input);
                                        Chess.getChessBoard().repaint();
                                        
                                        boolean inCheck = ChessMove.isChecked();
                                        
                                        yourTurn = true;
                                    } catch (IOException ex) {
                                        Logger.getLogger(ChessBoard.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                } 
                            }
                        }.start();
                    }
                    else // If you try ot go somewhere you can't, unselect the piece.
                    {
                        selectedPiece.getSquare().resetColor();
                        selectedPiece = null;
                        Chess.getChessBoard().repaint();
                    }
                }
            }
            else
            {
                e.consume();
            }
        }
        
        @Override 
        public void mouseReleased(MouseEvent e)
        {
        }
        
        @Override
        public void mouseClicked(MouseEvent e)
        {
            
        }   
        
        @Override
        public void mouseEntered(MouseEvent e)
        {
            
        }
        
        @Override
        public void mouseExited(MouseEvent e)
        {
            
        }
        
        public static String getIP()
        {
            return IP_ADDRESS;
        }
        
        public static boolean isYourTurn()
        {
            return yourTurn;
        }
    }