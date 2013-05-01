/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chessmaps.ChessPlayer;
import chessmaps.PieceMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Gennaro
 */
public class ChessPieceBox
{
    // <editor-fold defaultstate="collapsed" desc="ChessPieces">
    private ChessPiece blackPawn1;
    private ChessPiece blackPawn2;
    private ChessPiece blackPawn3;
    private ChessPiece blackPawn4;
    private ChessPiece blackPawn5;
    private ChessPiece blackPawn6;
    private ChessPiece blackPawn7;
    private ChessPiece blackPawn8;
    private ChessPiece blackRook1;
    private ChessPiece blackKnight1;
    private ChessPiece blackBishop1;
    private ChessPiece blackQueen;
    private ChessPiece blackKing;
    private ChessPiece blackBishop2;
    private ChessPiece blackKnight2;
    private ChessPiece blackRook2;
    
    private ChessPiece whitePawn1;
    private ChessPiece whitePawn2;
    private ChessPiece whitePawn3;
    private ChessPiece whitePawn4;
    private ChessPiece whitePawn5;
    private ChessPiece whitePawn6;
    private ChessPiece whitePawn7;
    private ChessPiece whitePawn8;
    private ChessPiece whiteRook1;
    private ChessPiece whiteKnight1;
    private ChessPiece whiteBishop1;
    private ChessPiece whiteQueen;
    private ChessPiece whiteKing;
    private ChessPiece whiteBishop2;
    private ChessPiece whiteKnight2;
    private ChessPiece whiteRook2;
    // </editor-fold>
    
    private static ChessPieceBox chessPieceBox;
    private CopyOnWriteArrayList<ChessPiece> pieceList;
    
    private static ChessPlayer player;
    private static ChessBoard chessBoard = null;
    
    private static String selectedPiece = "queen";
    
    private final static boolean DEBUG = false;
    
    protected ChessPieceBox()
    {
        if(chessBoard == null)
        {
            chessBoard = Chess.getChessBoard();
        }
        
        if(player == ChessPlayer.white) {
            // <editor-fold defaultstate="collapsed" desc="BoardLayoutWhite">
                blackPawn1 = new ChessPiece("blackPawn", 0, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn2 = new ChessPiece("blackPawn", 1, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn3 = new ChessPiece("blackPawn", 2, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn4 = new ChessPiece("blackPawn", 3, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn5 = new ChessPiece("blackPawn", 4, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn6 = new ChessPiece("blackPawn", 5, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn7 = new ChessPiece("blackPawn", 6, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn8 = new ChessPiece("blackPawn", 7, 1, PieceMap.PAWN, PieceMap.BLACK);
                
                blackRook1 = new ChessPiece("blackRook", 0, 0, PieceMap.ROOK, PieceMap.BLACK);
                blackKnight1 = new ChessPiece("blackKnight", 1, 0, PieceMap.KNIGHT, PieceMap.BLACK);
                blackBishop1 = new ChessPiece("blackBishop", 2, 0, PieceMap.BISHOP, PieceMap.BLACK);
                blackQueen = new ChessPiece("blackQueen", 3, 0, PieceMap.QUEEN, PieceMap.BLACK);
                blackKing = new ChessPiece("blackKing", 4, 0, PieceMap.KING, PieceMap.BLACK);
                blackBishop2 = new ChessPiece("blackBishop", 5, 0, PieceMap.BISHOP, PieceMap.BLACK);
                blackKnight2 = new ChessPiece("blackKnight", 6, 0, PieceMap.KNIGHT, PieceMap.BLACK);
                blackRook2 = new ChessPiece("blackRook", 7, 0, PieceMap.ROOK, PieceMap.BLACK);
                
                whitePawn1 = new ChessPiece("whitePawn", 0, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn2 = new ChessPiece("whitePawn", 1, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn3 = new ChessPiece("whitePawn", 2, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn4 = new ChessPiece("whitePawn", 3, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn5 = new ChessPiece("whitePawn", 4, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn6 = new ChessPiece("whitePawn", 5, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn7 = new ChessPiece("whitePawn", 6, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn8 = new ChessPiece("whitePawn", 7, 6, PieceMap.PAWN, PieceMap.WHITE);
                
                whiteRook1 = new ChessPiece("whiteRook", 0, 7, PieceMap.ROOK, PieceMap.WHITE);
                whiteKnight1 = new ChessPiece("whiteKnight", 1, 7, PieceMap.KNIGHT, PieceMap.WHITE);
                whiteBishop1 = new ChessPiece("whiteBishop", 2, 7, PieceMap.BISHOP, PieceMap.WHITE);
                whiteQueen = new ChessPiece("whiteQueen", 3, 7, PieceMap.QUEEN, PieceMap.WHITE);
                whiteKing = new ChessPiece("whiteKing", 4, 7, PieceMap.KING, PieceMap.WHITE);
                whiteBishop2 = new ChessPiece("whiteBishop", 5, 7, PieceMap.BISHOP, PieceMap.WHITE);
                whiteKnight2 = new ChessPiece("whiteKnight", 6, 7, PieceMap.KNIGHT, PieceMap.WHITE);
                whiteRook2 = new ChessPiece("whiteRook", 7, 7, PieceMap.ROOK, PieceMap.WHITE);
                // </editor-fold>
        }
        else if(player == ChessPlayer.black) {
            // <editor-fold defaultstate="collapsed" desc="BoardLayoutBlack">
            whitePawn1 = new ChessPiece("whitePawn", 0, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn2 = new ChessPiece("whitePawn", 1, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn3 = new ChessPiece("whitePawn", 2, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn4 = new ChessPiece("whitePawn", 3, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn5 = new ChessPiece("whitePawn", 4, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn6 = new ChessPiece("whitePawn", 5, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn7 = new ChessPiece("whitePawn", 6, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn8 = new ChessPiece("whitePawn", 7, 1, PieceMap.PAWN, PieceMap.WHITE);

            whiteRook1 = new ChessPiece("whiteRook", 0, 0, PieceMap.ROOK, PieceMap.WHITE);
            whiteKnight1 = new ChessPiece("whiteKnight", 1, 0, PieceMap.KNIGHT, PieceMap.WHITE);
            whiteBishop1 = new ChessPiece("whiteBishop", 2, 0, PieceMap.BISHOP, PieceMap.WHITE);
            whiteQueen = new ChessPiece("whiteQueen", 4, 0, PieceMap.QUEEN, PieceMap.WHITE);
            whiteKing = new ChessPiece("whiteKing", 3, 0, PieceMap.KING, PieceMap.WHITE);
            whiteBishop2 = new ChessPiece("whiteBishop", 5, 0, PieceMap.BISHOP, PieceMap.WHITE);
            whiteKnight2 = new ChessPiece("whiteKnight", 6, 0, PieceMap.KNIGHT, PieceMap.WHITE);
            whiteRook2 = new ChessPiece("whiteRook", 7, 0, PieceMap.ROOK, PieceMap.WHITE);

            blackPawn1 = new ChessPiece("blackPawn", 0, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn2 = new ChessPiece("blackPawn", 1, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn3 = new ChessPiece("blackPawn", 2, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn4 = new ChessPiece("blackPawn", 3, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn5 = new ChessPiece("blackPawn", 4, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn6 = new ChessPiece("blackPawn", 5, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn7 = new ChessPiece("blackPawn", 6, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn8 = new ChessPiece("blackPawn", 7, 6, PieceMap.PAWN, PieceMap.BLACK);

            blackRook1 = new ChessPiece("blackRook", 0, 7, PieceMap.ROOK, PieceMap.BLACK);
            blackKnight1 = new ChessPiece("blackKnight", 1, 7, PieceMap.KNIGHT, PieceMap.BLACK);
            blackBishop1 = new ChessPiece("blackBishop", 2, 7, PieceMap.BISHOP, PieceMap.BLACK);
            blackQueen = new ChessPiece("blackQueen", 4, 7, PieceMap.QUEEN, PieceMap.BLACK);
            blackKing = new ChessPiece("blackKing", 3, 7, PieceMap.KING, PieceMap.BLACK);
            blackBishop2 = new ChessPiece("blackBishop", 5, 7, PieceMap.BISHOP, PieceMap.BLACK);
            blackKnight2 = new ChessPiece("blackKnight", 6, 7, PieceMap.KNIGHT, PieceMap.BLACK);
            blackRook2 = new ChessPiece("blackRook", 7, 7, PieceMap.ROOK, PieceMap.BLACK);
            // </editor-fold>
        }  
        
        pieceList = new CopyOnWriteArrayList<>();
        
        // <editor-fold defaultstate="collapsed" desc="Add pieces">
        pieceList.add(whitePawn1);
        pieceList.add(whitePawn2);
        pieceList.add(whitePawn3);
        pieceList.add(whitePawn4);
        pieceList.add(whitePawn5);
        pieceList.add(whitePawn6);
        pieceList.add(whitePawn7);
        pieceList.add(whitePawn8);
        
        pieceList.add(whiteRook1);
        pieceList.add(whiteKnight1);
        pieceList.add(whiteBishop1);
        pieceList.add(whiteQueen);
        pieceList.add(whiteKing);
        pieceList.add(whiteBishop2);
        pieceList.add(whiteKnight2);
        pieceList.add(whiteRook2);
        
        pieceList.add(blackPawn1);
        pieceList.add(blackPawn2);
        pieceList.add(blackPawn3);
        pieceList.add(blackPawn4);
        pieceList.add(blackPawn5);
        pieceList.add(blackPawn6);
        pieceList.add(blackPawn7);
        pieceList.add(blackPawn8);
        
        pieceList.add(blackRook1);
        pieceList.add(blackKnight1);
        pieceList.add(blackBishop1);
        pieceList.add(blackQueen);
        pieceList.add(blackKing);
        pieceList.add(blackBishop2);
        pieceList.add(blackKnight2);
        pieceList.add(blackRook2);
        // </editor-fold>
    }
    
    /**
     * Designate the player color to allow the piece list to be instantiated
     * properly.
     * @param player 
     */
    public static void prepareInstance(ChessPlayer player)
    {
        ChessPieceBox.player = player;
    }
    
    /**
     * Get the box of chess pieces. Note: Requires that prepare instance was previously called.
     */
    public static ChessPieceBox getInstance()
    {
        if(player == null)
        {
            throw new RuntimeException("Instance not yet prepared.");
        }
        if(chessPieceBox == null)
        {
            chessPieceBox = new ChessPieceBox();
        }

        return chessPieceBox;
    }
    
    /**
     * For this method, xPos and yPos are actually pixel coordinates.
     * @param xPos
     * @param yPos
     * @return 
     */
    public ChessPiece getPieceFromPixels(int xPos, int yPos)
    {
        for(ChessPiece piece : pieceList)
        {
            // If the current piece iteration is on the square containing the input location, return that piece.
            if(piece.getSquare().contains(xPos, yPos, chessBoard.getPanelWidth(), chessBoard.getPanelHeight()))
            {
                return piece;
            }
        }
        // No piece on that square.
        return null;
    }
    
    /**
     * Returns the piece on the specified square if it exists. Otherwise, returns null.
     * @param xPos The x position, between 0 and 7 inclusive.
     * @param yPos The y position, between 0 and 7 inclusive.
     * @return 
     */
    public ChessPiece getPieceFromSquare(int xPos, int yPos)
    {
        if(xPos > 7 || yPos > 7)
        {
            return null;
        }
        
        //ChessPiece piece = null;
        
        for(ChessPiece piece: pieceList) /*Iterator<ChessPiece> iter = pieceList.iterator(); iter.hasNext(); piece = iter.next()*/
        {
            if(piece == null)
            {
                Console.err.println("One of the pieces is null...");
                continue;
            }
            if(piece.getSquare().getXPos() == 8)
            {
                pieceList.remove(piece);
                //iter.remove();
            }
            if(piece.getSquare().getXPos() == xPos && piece.getSquare().getYPos() == yPos)
            {
                return piece;
            }
        }
        return null;
    }

    public ChessPlayer getPlayer()
    {
        return player;
    }
    
    public CopyOnWriteArrayList<ChessPiece> getPieceList()
    {
        return pieceList;
    }
    
    /**
     * This method moves a piece as well as updating the hasMoved value of that piece.
     */
    public void movePiece(int oldX, int oldY, int newX, int newY, boolean hasMoved, String pieceURL)
    {
        ChessPiece piece = getPieceFromSquare(oldX, oldY);
        if(piece == null)
        {
            return;
        }
        if(piece.getPieceURL() == null ? pieceURL != null : !piece.getPieceURL().equals(pieceURL))
        {
            piece.setPieceURL(pieceURL);
            Chess.getChessBoard().resizePieces();
        }
        if(hasMoved)
        {
            piece.setMoved();
        }
        
        if(newX == 8 && DEBUG)
        {
            Console.message.println("From square: " + piece.getSquare().toString());
            Console.message.println("Piece moved: " + piece.toString());
        }
        piece.setSquare(newX, newY);
    }
    
    public void resetPreviousSquares()
    {
        for(ChessPiece piece : pieceList)
        {
            piece.resetPreviousSquare();
        }
    }
    
    public void resetBoard()
    {
        pieceList.removeAll(pieceList);
        
        if(player == ChessPlayer.white) {
            // <editor-fold defaultstate="collapsed" desc="BoardLayoutWhite">
                blackPawn1 = new ChessPiece("blackPawn", 0, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn2 = new ChessPiece("blackPawn", 1, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn3 = new ChessPiece("blackPawn", 2, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn4 = new ChessPiece("blackPawn", 3, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn5 = new ChessPiece("blackPawn", 4, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn6 = new ChessPiece("blackPawn", 5, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn7 = new ChessPiece("blackPawn", 6, 1, PieceMap.PAWN, PieceMap.BLACK);
                blackPawn8 = new ChessPiece("blackPawn", 7, 1, PieceMap.PAWN, PieceMap.BLACK);
                
                blackRook1 = new ChessPiece("blackRook", 0, 0, PieceMap.ROOK, PieceMap.BLACK);
                blackKnight1 = new ChessPiece("blackKnight", 1, 0, PieceMap.KNIGHT, PieceMap.BLACK);
                blackBishop1 = new ChessPiece("blackBishop", 2, 0, PieceMap.BISHOP, PieceMap.BLACK);
                blackQueen = new ChessPiece("blackQueen", 3, 0, PieceMap.QUEEN, PieceMap.BLACK);
                blackKing = new ChessPiece("blackKing", 4, 0, PieceMap.KING, PieceMap.BLACK);
                blackBishop2 = new ChessPiece("blackBishop", 5, 0, PieceMap.BISHOP, PieceMap.BLACK);
                blackKnight2 = new ChessPiece("blackKnight", 6, 0, PieceMap.KNIGHT, PieceMap.BLACK);
                blackRook2 = new ChessPiece("blackRook", 7, 0, PieceMap.ROOK, PieceMap.BLACK);
                
                whitePawn1 = new ChessPiece("whitePawn", 0, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn2 = new ChessPiece("whitePawn", 1, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn3 = new ChessPiece("whitePawn", 2, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn4 = new ChessPiece("whitePawn", 3, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn5 = new ChessPiece("whitePawn", 4, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn6 = new ChessPiece("whitePawn", 5, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn7 = new ChessPiece("whitePawn", 6, 6, PieceMap.PAWN, PieceMap.WHITE);
                whitePawn8 = new ChessPiece("whitePawn", 7, 6, PieceMap.PAWN, PieceMap.WHITE);
                
                whiteRook1 = new ChessPiece("whiteRook", 0, 7, PieceMap.ROOK, PieceMap.WHITE);
                whiteKnight1 = new ChessPiece("whiteKnight", 1, 7, PieceMap.KNIGHT, PieceMap.WHITE);
                whiteBishop1 = new ChessPiece("whiteBishop", 2, 7, PieceMap.BISHOP, PieceMap.WHITE);
                whiteQueen = new ChessPiece("whiteQueen", 3, 7, PieceMap.QUEEN, PieceMap.WHITE);
                whiteKing = new ChessPiece("whiteKing", 4, 7, PieceMap.KING, PieceMap.WHITE);
                whiteBishop2 = new ChessPiece("whiteBishop", 5, 7, PieceMap.BISHOP, PieceMap.WHITE);
                whiteKnight2 = new ChessPiece("whiteKnight", 6, 7, PieceMap.KNIGHT, PieceMap.WHITE);
                whiteRook2 = new ChessPiece("whiteRook", 7, 7, PieceMap.ROOK, PieceMap.WHITE);
                // </editor-fold>
        }
        else if(player == ChessPlayer.black) {
            // <editor-fold defaultstate="collapsed" desc="BoardLayoutBlack">
            whitePawn1 = new ChessPiece("whitePawn", 0, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn2 = new ChessPiece("whitePawn", 1, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn3 = new ChessPiece("whitePawn", 2, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn4 = new ChessPiece("whitePawn", 3, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn5 = new ChessPiece("whitePawn", 4, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn6 = new ChessPiece("whitePawn", 5, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn7 = new ChessPiece("whitePawn", 6, 1, PieceMap.PAWN, PieceMap.WHITE);
            whitePawn8 = new ChessPiece("whitePawn", 7, 1, PieceMap.PAWN, PieceMap.WHITE);

            whiteRook1 = new ChessPiece("whiteRook", 0, 0, PieceMap.ROOK, PieceMap.WHITE);
            whiteKnight1 = new ChessPiece("whiteKnight", 1, 0, PieceMap.KNIGHT, PieceMap.WHITE);
            whiteBishop1 = new ChessPiece("whiteBishop", 2, 0, PieceMap.BISHOP, PieceMap.WHITE);
            whiteQueen = new ChessPiece("whiteQueen", 4, 0, PieceMap.QUEEN, PieceMap.WHITE);
            whiteKing = new ChessPiece("whiteKing", 3, 0, PieceMap.KING, PieceMap.WHITE);
            whiteBishop2 = new ChessPiece("whiteBishop", 5, 0, PieceMap.BISHOP, PieceMap.WHITE);
            whiteKnight2 = new ChessPiece("whiteKnight", 6, 0, PieceMap.KNIGHT, PieceMap.WHITE);
            whiteRook2 = new ChessPiece("whiteRook", 7, 0, PieceMap.ROOK, PieceMap.WHITE);

            blackPawn1 = new ChessPiece("blackPawn", 0, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn2 = new ChessPiece("blackPawn", 1, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn3 = new ChessPiece("blackPawn", 2, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn4 = new ChessPiece("blackPawn", 3, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn5 = new ChessPiece("blackPawn", 4, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn6 = new ChessPiece("blackPawn", 5, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn7 = new ChessPiece("blackPawn", 6, 6, PieceMap.PAWN, PieceMap.BLACK);
            blackPawn8 = new ChessPiece("blackPawn", 7, 6, PieceMap.PAWN, PieceMap.BLACK);

            blackRook1 = new ChessPiece("blackRook", 0, 7, PieceMap.ROOK, PieceMap.BLACK);
            blackKnight1 = new ChessPiece("blackKnight", 1, 7, PieceMap.KNIGHT, PieceMap.BLACK);
            blackBishop1 = new ChessPiece("blackBishop", 2, 7, PieceMap.BISHOP, PieceMap.BLACK);
            blackQueen = new ChessPiece("blackQueen", 4, 7, PieceMap.QUEEN, PieceMap.BLACK);
            blackKing = new ChessPiece("blackKing", 3, 7, PieceMap.KING, PieceMap.BLACK);
            blackBishop2 = new ChessPiece("blackBishop", 5, 7, PieceMap.BISHOP, PieceMap.BLACK);
            blackKnight2 = new ChessPiece("blackKnight", 6, 7, PieceMap.KNIGHT, PieceMap.BLACK);
            blackRook2 = new ChessPiece("blackRook", 7, 7, PieceMap.ROOK, PieceMap.BLACK);
            // </editor-fold>
        }  
        
        // <editor-fold defaultstate="collapsed" desc="Add pieces">
        pieceList.add(whitePawn1);
        pieceList.add(whitePawn2);
        pieceList.add(whitePawn3);
        pieceList.add(whitePawn4);
        pieceList.add(whitePawn5);
        pieceList.add(whitePawn6);
        pieceList.add(whitePawn7);
        pieceList.add(whitePawn8);
        
        pieceList.add(whiteRook1);
        pieceList.add(whiteKnight1);
        pieceList.add(whiteBishop1);
        pieceList.add(whiteQueen);
        pieceList.add(whiteKing);
        pieceList.add(whiteBishop2);
        pieceList.add(whiteKnight2);
        pieceList.add(whiteRook2);
        
        pieceList.add(blackPawn1);
        pieceList.add(blackPawn2);
        pieceList.add(blackPawn3);
        pieceList.add(blackPawn4);
        pieceList.add(blackPawn5);
        pieceList.add(blackPawn6);
        pieceList.add(blackPawn7);
        pieceList.add(blackPawn8);
        
        pieceList.add(blackRook1);
        pieceList.add(blackKnight1);
        pieceList.add(blackBishop1);
        pieceList.add(blackQueen);
        pieceList.add(blackKing);
        pieceList.add(blackBishop2);
        pieceList.add(blackKnight2);
        pieceList.add(blackRook2);
        // </editor-fold>
        
        Chess.getChessBoard().resizePieces();
        ChessSquareBox.getInstance().resetSquareColors();
    }

    public void setSelectedPiece(String selectedPiece)
    {
        ChessPieceBox.selectedPiece = selectedPiece;
    }

    public String getSelectedPiece()
    {
        return selectedPiece;
    }
    
    public ChessPiece getKing()
    {
        if(whiteKing == null || blackKing == null)
        {
            Console.err.println("Both kings are null...");
        }
        if(player == ChessPlayer.white)
        {
            return whiteKing;
        }
        else
        {
            return blackKing;
        }
    }
}
