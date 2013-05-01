/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chesslisteners;

import chess.Chess;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 *
 * @author Gennaro
 */
public class ResizeListener implements ComponentListener{
    @Override
    public void componentResized(ComponentEvent e) {
        Chess.getChessBoard().resizePieces();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
