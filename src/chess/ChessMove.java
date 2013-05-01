/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chessmaps.PieceMap;
import chessmaps.ChessPlayer;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Gennaro
 */
public abstract class ChessMove 
{
    static ArrayList<ChessSquare> coloredSquaresList = new ArrayList<>();
    public static int castleSide;
    public final static int KING_SIDE = 0;
    public final static int QUEEN_SIDE = 1;
    
    private static ChessBoard board = Chess.getChessBoard();
    private static ChessPieceBox chessPieceBox;
   
    public static boolean canMove(ChessPiece piece, ChessSquare square)
    { 
        if(chessPieceBox == null)
        {
            chessPieceBox = ChessPieceBox.getInstance();
        }
        
        if((board.getPlayer() == ChessPlayer.white) && (piece.getPieceColor() == PieceMap.BLACK))
        {
            return false;
        }
        else if(board.getPlayer() == ChessPlayer.black && piece.getPieceColor() == PieceMap.WHITE)
        {
            return false;
        }
        switch(piece.getPieceType()) 
        {
            case PieceMap.PAWN:
                return handlePawn(piece, square);
            case PieceMap.ROOK:
                return handleRook(piece, square);
            case PieceMap.KNIGHT:
                return handleKnight(piece, square);
            case PieceMap.BISHOP:
               return handleBishop(piece, square);
            case PieceMap.QUEEN:
                return handleQueen(piece, square);
            case PieceMap.KING:
                return handleKing(piece, square);
        }
        Console.err.println("Invalid piece type.");
        return false;
    }
    
    /**
     * If this version is called, the return will be whether your opponent can move to a square, not you.
     */
    public static boolean canMove(ChessPiece piece, ChessSquare square, boolean forCheck)
    {
        if(chessPieceBox == null)
        {
            chessPieceBox = ChessPieceBox.getInstance();
        }
        
        if((board.getPlayer() == ChessPlayer.black) && (piece.getPieceColor() == PieceMap.BLACK))
        {
            return false;
        }
        else if(board.getPlayer() == ChessPlayer.white && piece.getPieceColor() == PieceMap.WHITE)
        {
            return false;
        }
        switch(piece.getPieceType()) 
        {
            case PieceMap.PAWN:
                return handlePawn(piece, square);
            case PieceMap.ROOK:
                return handleRook(piece, square);
            case PieceMap.KNIGHT:
                return handleKnight(piece, square);
            case PieceMap.BISHOP:
               return handleBishop(piece, square);
            case PieceMap.QUEEN:
                return handleQueen(piece, square);
            case PieceMap.KING:
                return handleKing(piece, square);
        }
        Console.err.println("Invalid piece type.");
        return false;
    }
   
    public static boolean isChecked()
    {
        if(chessPieceBox == null)
        {
            chessPieceBox = ChessPieceBox.getInstance();
        }
        
        ChessPiece king = chessPieceBox.getKing();
        
        CopyOnWriteArrayList<ChessPiece> pieceList = chessPieceBox.getPieceList();
        for(ChessPiece piece : pieceList)
        {
            if(canMove(piece, king.getSquare(), true))
            {
                Console.admin.println("Check");
                return true;
            }
        }
        return false;
    }
    
    public static boolean canCastle(ChessPiece piece, ChessSquare square)
    {
        ChessSquare currentSquare = piece.getSquare();
        
        if(piece.getPieceType() != PieceMap.KING)
        {
            return false;
        }
        else if(piece.hasMoved() == true)
        {
            return false;
        }
        else if(Math.abs(currentSquare.getXPos() - square.getXPos()) == 2 && (currentSquare.getYPos() == square.getYPos()))
        {
            // piece1 is the piece 3 squares in the selected direction
            ChessPiece piece1 = chessPieceBox.getPieceFromSquare(square.getXPos() + (square.getXPos() - currentSquare.getXPos())/2,
                    currentSquare.getYPos());
            // piece2 is the piece 4 squares in the selected direction 
            ChessPiece piece2 = chessPieceBox.getPieceFromSquare(square.getXPos() + (square.getXPos() - currentSquare.getXPos()),
                    currentSquare.getYPos());
            // If neither of those squares has a piece, can't castle.
            if(piece1 == null && piece2 == null)
            {
                return false;
            }
            // If the first square has a piece on it, test to see if it's a rook that can castle.
            if(piece1 != null)
            {
                if(piece1.getPieceType() == PieceMap.ROOK && !piece1.hasMoved() && noIntermediatePieces(piece, piece1.getSquare()))
                {
                    castleSide = KING_SIDE;
                    return true;
                }
            }
            if(piece2 != null)
            {
                if(piece2.getPieceType() == PieceMap.ROOK && !piece2.hasMoved() && noIntermediatePieces(piece, piece2.getSquare()))
                {
                    castleSide = QUEEN_SIDE;
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean handlePawn(ChessPiece piece, ChessSquare square)
    {
        int move;
        boolean toReturn = false;
        
        if(board.getPlayer() == ChessPlayer.white)
        {
            move = (piece.getPieceColor() == PieceMap.WHITE) ? 1 : -1;
        }
        else if(board.getPlayer() == ChessPlayer.black)
        {
            move = (piece.getPieceColor() == PieceMap.WHITE) ? -1 : 1;
        }
        else
        {
            move = 0;
            Console.err.println("Player color not chosen.");
        }

        // If the pawn hasn't move yet, it can move forward two.
        if(!piece.hasMoved() && square.getXPos() == piece.getSquare().getXPos() &&
                piece.getSquare().getYPos()-2*move == square.getYPos() && noIntermediatePieces(piece, square) &&
                targetSquareFree(square))
        {
            toReturn = true;
        }
        
        // Target square of one forward.
        if(square.getXPos() == piece.getSquare().getXPos() && piece.getSquare().getYPos()-1*move == square.getYPos() &&
                targetSquareFree(square))
        {
            toReturn = true;
        }
        // Either diagonal square.
        if(square.getXPos() == piece.getSquare().getXPos() - 1 && square.getYPos() == piece.getSquare().getYPos() - move && 
                targetSquareAttackable(piece, square))
        {
            toReturn = true;
        }
        if(square.getXPos() == piece.getSquare().getXPos() + 1 && square.getYPos() == piece.getSquare().getYPos() - move &&
                targetSquareAttackable(piece, square))
        {
            toReturn = true;
        }
        
        return toReturn;
    }
    
    private static boolean handleRook(ChessPiece piece, ChessSquare square)
    {
        if(((square.getXPos() == piece.getSquare().getXPos()) || (square.getYPos() == piece.getSquare().getYPos())) &&
                noIntermediatePieces(piece, square) &&
                targetSquareAttackable(piece, square))
        {
            return true;
        }
        else
        {
            /*Console.admin.println("Piece: {" + piece.getSquare().xPos + "," + piece.getSquare().yPos + "}");
            Console.admin.println("Destination: {" + square.xPos + "," + square.yPos + "}");
            Console.admin.println("No intermediates: " + noIntermediatePieces(piece, square));
            Console.admin.println("Attackable: " + targetSquareAttackable(piece, square)); */
            return false;
        }
    }
    
    private static boolean handleKnight(ChessPiece piece, ChessSquare square)
    {
        if(((Math.abs(piece.getSquare().getXPos() - square.getXPos()) == 2 && Math.abs(piece.getSquare().getYPos() - square.getYPos()) == 1) ||
                (Math.abs(piece.getSquare().getXPos() - square.getXPos()) == 1 && Math.abs(piece.getSquare().getYPos() - square.getYPos()) == 2)) &&
                targetSquareAttackable(piece, square))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private static boolean handleBishop(ChessPiece piece, ChessSquare square)
    {
        if((Math.abs(square.getXPos() - piece.getSquare().getXPos()) == Math.abs(square.getYPos() - piece.getSquare().getYPos())) &&
                noIntermediatePieces(piece, square) &&
                targetSquareAttackable(piece, square)) 
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private static boolean handleQueen(ChessPiece piece, ChessSquare square)
    {
        if(((Math.abs(square.getXPos() - piece.getSquare().getXPos()) == Math.abs(square.getYPos() - piece.getSquare().getYPos())) ||
                (piece.getSquare().getXPos() == square.getXPos()) ||
                (piece.getSquare().getYPos() == square.getYPos())) &&
                noIntermediatePieces(piece, square) &&
                targetSquareAttackable(piece, square))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private static boolean handleKing(ChessPiece piece, ChessSquare square)
    {
        if(((Math.abs(piece.getSquare().getXPos() - square.getXPos()) == 1 && piece.getSquare().getYPos() == square.getYPos()) ||
                (Math.abs(piece.getSquare().getYPos() - square.getYPos()) == 1 && piece.getSquare().getXPos() == square.getXPos()) ||
                ((Math.abs(piece.getSquare().getYPos() - square.getYPos()) == 1) && (Math.abs(piece.getSquare().getXPos() - square.getXPos()) == 1))) &&
                targetSquareAttackable(piece, square))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private static boolean targetSquareFree(ChessSquare square) // Only for the pawns, otherwise, use targetSquareAttackable(...).
    {
        return (chessPieceBox.getPieceFromSquare(square.getXPos(), square.getYPos()) == null) ? true : false;
    }
    
    private static boolean targetSquareAttackable(ChessPiece piece, ChessSquare square)
    {
        boolean pieceColor = piece.getPieceColor();
        ChessPiece targetPiece = chessPieceBox.getPieceFromSquare(square.getXPos(), square.getYPos());
        if(targetPiece == null)
        {
            if(piece.getPieceType() != PieceMap.PAWN) {
                return true;
            } // If there's no piece there, let the piece move there.
            else
            { // Unless you're a pawn.
                return false;
            }
        }
        else if(targetPiece.getPieceColor() == pieceColor)
        {
            return false;   // If the piece there is the same color, don't allow the move.
        }
        else
        {
            return true;    // If the piece there is the other color, allow the move.
        }
    }
    
    private static boolean noIntermediatePieces(ChessPiece piece, ChessSquare targetSquare)
    {
        ChessSquare currentSquare = piece.getSquare();
        
        // The piece is already on that square.
        if(currentSquare.getXPos() == targetSquare.getXPos() && currentSquare.getYPos() == targetSquare.getYPos())
        {
            Console.err.println("This branch should never be called.");
            return false;
        }
        // x is same, y is different
        else if(currentSquare.getXPos() == targetSquare.getXPos())
        {
            if(currentSquare.getYPos() > targetSquare.getYPos())
            {
                for(int i = 1; i < (currentSquare.getYPos() - targetSquare.getYPos()); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos(), currentSquare.getYPos()-i) != null)
                    {
                        return false;
                    }
                }
            }
            else 
            {
                for(int i = 1; i< (targetSquare.getYPos() - currentSquare.getYPos()); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos(), currentSquare.getYPos()+i) != null)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        // y is same, x is different
        else if(currentSquare.getYPos() == targetSquare.getYPos())
        {
            if(currentSquare.getXPos() > targetSquare.getXPos())
            {
                for(int i = 1; i < (currentSquare.getXPos() - targetSquare.getXPos()); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()-i, currentSquare.getYPos()) != null)
                    {
                        return false;
                    }
                }
            }
            else 
            {
                for(int i = 1; i< (targetSquare.getXPos() - currentSquare.getXPos()); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()+i, currentSquare.getYPos()) != null)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        
        else    // xPos and yPos are both different
        {
            // Moving down and left.
            if(currentSquare.getXPos() > targetSquare.getXPos() && currentSquare.getYPos() < targetSquare.getYPos()) 
            {
                for(int i=1; i<currentSquare.getXPos()-targetSquare.getXPos(); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()-i, currentSquare.getYPos()+i) != null)
                    {
                        //Console.err.println("Line 224, down left");
                        return false;
                    }
                }
                return true;
            }
            // Moving up and left.
            else if(currentSquare.getXPos() > targetSquare.getXPos() && currentSquare.getYPos() > targetSquare.getYPos())
            {
                for(int i=1; i<currentSquare.getXPos()-targetSquare.getXPos(); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()-i, currentSquare.getYPos()-i) != null)
                    {
                        //Console.err.println("Line 237, up left.");
                        return false;
                    }
                }
                return true;
            }
            // Moving down and right.
            else if(currentSquare.getXPos() < targetSquare.getXPos() && currentSquare.getYPos() < targetSquare.getYPos())
            {
                for(int i=1; i<targetSquare.getXPos()-currentSquare.getXPos(); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()+i, currentSquare.getYPos()+i) != null)
                    {
                        //Console.err.println("Line 250, down right.");
                        return false;
                    }
                }
                return true;
            }
            // Moving up and right.
            else if(currentSquare.getXPos() < targetSquare.getXPos() && currentSquare.getYPos() > targetSquare.getYPos())
            {
                for(int i=1; i<targetSquare.getXPos()-currentSquare.getXPos(); i++)
                {
                    if(chessPieceBox.getPieceFromSquare(currentSquare.getXPos()+i, currentSquare.getYPos()-i) != null)
                    {
                        //Console.err.println("Line 263, up right.");
                        return false;
                    }
                }
                return true;
            }
            else
            {
                Console.err.println("Should not have hit this line - noIntermediatePieces().");
                return false;
            }
        }            
    }
}
