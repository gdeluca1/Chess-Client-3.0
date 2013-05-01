/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chesslisteners;

import chess.Chess;
import chess.Console;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import messaging.Messenger;

/**
 *
 * @author Gennaro
 */
public class MenuListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == Chess.getGMenuBar().getColorItem())
        {
            Color choice = JColorChooser.showDialog(null, "Choose a color", Console.getMessageColor());
            Console.setMessageColor(choice);
        }
        
        else if(e.getSource() == Chess.getGMenuBar().getDefaultItem())
        {
            Console.resetMessageColor();
        }
        
        else if(e.getSource() == Chess.getGMenuBar().getNewItem())
        {
            Messenger.getOut().sendLine("/new");
        }
    }
}
