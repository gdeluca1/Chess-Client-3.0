/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import chess.Chess;
import chess.Console;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import messaging.Messenger;

/**
 *
 * @author Gennaro
 */
public class SendButtonListener implements ActionListener
{
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
       sendMessage();
    }
    
    public static void sendMessage()
    {
        String s = Chess.getInputArea().getText();
        Messenger.getOut().sendLine(s);
        Chess.getInputArea().setText("");
        if(!s.equals(""))
        {
            if(!s.equals("/new") && !s.equals("/accept") && !s.equals("/refuse") &&
                    !s.equals("/disable") && !s.equals("/enable") && !s.equals("/help") && !s.equals("/?") && !s.equals("/rematch") &&
                    !s.equals("/acceptUndo") && !s.equals("/refuseUndo") && !s.equals("/undo"))
            {
                Console.message.println("me: " + s);
            }
        }
    }
    
}
