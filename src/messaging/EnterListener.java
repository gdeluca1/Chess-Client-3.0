/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Gennaro
 */
public class EnterListener extends AbstractAction
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        SendButtonListener.sendMessage();
    }
}
