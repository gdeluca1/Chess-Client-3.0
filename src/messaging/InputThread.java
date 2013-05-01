/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import chess.Chess;
import chess.ChessPieceBox;
import chess.Console;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Gennaro
 */
public class InputThread extends Thread
{
    Socket socket;
    InputStreamReader isr;
    BufferedReader in;
    boolean firstTime = true;
    private String computerName = "Default";
    private double version = 1.0;
    // open is true so long as the socket is open.
    private static boolean open = true;
    
    public InputThread(Socket socket)
    {
        try
        {
            this.socket = socket;
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        while(open)
        {
            try
            {
                String inString = in.readLine();
                if(!firstTime)
                {
                    if(inString.equals("/new") || inString.equals("/rematch"))
                    {
                        if(!Messenger.getOut().isDisabled())
                        {
                            int choice = JOptionPane.showConfirmDialog(null,"Your opponent has offered a rematch. Would you like to accept?", null,
                                    JOptionPane.YES_NO_OPTION);
                            if(choice == JOptionPane.YES_OPTION)
                            {
                                Console.out.println("Rematch accepted.");
                                Messenger.getOut().sendLine("/accept");
                                ChessPieceBox.getInstance().resetBoard();
                            }
                            else
                            {
                                Console.out.println("Rematch refused.");
                                Messenger.getOut().sendLine("/refuse");
                            }
                        }
                        else
                        {
                            Console.err.println("Ignored opponents rematch request");
                            Messenger.getOut().sendLine("/ignore");
                        }
                    }
                    else if(inString.equals("/undo"))
                    {
                        int choice = JOptionPane.showConfirmDialog(null,"Your opponent has offered to undo. Would you like to accept?", null,
                                    JOptionPane.YES_NO_OPTION);
                        if(choice == JOptionPane.YES_OPTION)
                        {
                            Console.out.println("Undo accepted.");
                            Messenger.getOut().sendLine("/acceptUndo");
                            // TODO: Undo
                        }
                        else
                        {
                            Console.out.println("Undo refused.");
                            Messenger.getOut().sendLine("/refuseUndo");
                        }
                    }
                    else if(inString.equals("/acceptUndo"))
                    {
                        // TODO: undo.
                        Console.out.println("Undo accepted.");
                        Messenger.getOut().setOfferedUndo(false);
                    }
                    else if(inString.equals("/refuseUndo"))
                    {
                        Console.out.println("Undo refused.");
                        Messenger.getOut().setOfferedUndo(false);
                    }
                    else if(inString.equals("/accept") && Messenger.getOut().getOfferedRematch())
                    {
                        Console.out.println("Rematch accepted.");
                        Messenger.getOut().setOfferedRematch(false);
                        ChessPieceBox.getInstance().resetBoard();
                    }
                    else if(inString.equals("/refuse"))
                    {
                        Console.out.println("Rematch refused.");
                        Messenger.getOut().setOfferedRematch(false);
                    }
                    else if(inString.equals("/ignore"))
                    {
                        Console.out.println("Rematch ignored.");
                        Messenger.getOut().setOfferedRematch(false);
                    }
                    else
                    {
                        Console.message.println(computerName + ": " + inString);
                    }
                }
                else
                {
                    String[] array = inString.split("~");
                    computerName = array[0];
                    version = Double.parseDouble(array[1]);
                    if(version < Chess.getVersionNumber())
                    {
                        Console.err.println("Warning, your friend is using an older version of Chess.");
                    }
                    else if(version > Chess.getVersionNumber())
                    {
                        Console.err.println("Warning! You are using an older version of Chess. You may experience technical difficulties."
                                + " Please contact your local distributor for the newest version.");
                    }
                    firstTime = false;
                }
            } 
            catch(SocketException ex)
            {
                Console.err.println("Connection error. Communications ended.");
                open = false;
            }
            catch (IOException ex)
            {
                Logger.getLogger(InputThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static boolean isOpen()
    {
        return open;
    }
}
