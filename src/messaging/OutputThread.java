/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import chess.Chess;
import chess.Console;
import chesslisteners.MoveListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gennaro
 */
public class OutputThread extends Thread
{
    Socket socket;
    DataOutputStream out;
    static String s = "";
    
    String computerName = "Default";
    private boolean offeredRematch = false;
    private boolean offeredUndo = false;
    private boolean disabled = false;
    
    public OutputThread(Socket socket)
    {
        try
        {
            computerName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex)
        {
            computerName = "Error";
        }
        
        try
        {
            String endString = computerName.substring(computerName.length()-3);
            if(endString.toLowerCase().equals("-PC".toLowerCase()))
            {
                computerName = computerName.substring(0, computerName.length()-3);
            }
        }
        catch(IndexOutOfBoundsException ex)
        {
            computerName = "Error2";
        }
        finally
        {
            s = computerName + "~" + Chess.getVersionNumber();
        }
        
        try
        {
            this.socket = socket;
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex)
        {
            Logger.getLogger(OutputThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        System.out.println("Out start called.");
        while(true)
        {
            if(!"".equals(s))
            {
                System.out.println("String identified.");
                if(s.equals("/new") && MoveListener.isYourTurn())
                {
                    offeredRematch = true;
                    Console.out.println("Rematch offer made.");
                }
                else if(s.equals("/undo"))
                {
                    offeredUndo = true;
                    Console.out.println("Undo offer made.");
                }
                else if(s.equals("/new") && !MoveListener.isYourTurn())
                {
                    Console.err.println("Please wait until your turn to offer a rematch.");
                    s = "";
                    continue;
                }
                else if(s.equals("/disable"))
                {
                    Console.admin.println("Rematch offers disabled");
                    disabled = true;
                    s = "";
                    continue;
                }
                else if(s.equals("/help") || s.equals("/?"))
                    {
                        Console.out.println("/new or /rematch to request a rematch");
                        Console.out.println("/accept to accept an active rematch offer.");
                        Console.out.println("/refuse to deny an active rematch offer.");
                        Console.out.println("/ignore to ignore an active rematch offer.");
                        Console.out.println("/disable to ignore any further rematch offers.");
                        Console.out.println("/enable to re-enable further rematch offers.");
                        s = "";
                        continue;
                    }
                else if(s.equals("/enable"))
                {
                    Console.admin.println("Rematch offers enabled.");
                    disabled = false;
                    s = "";
                    continue;
                }
                try
                {
                    out.writeBytes(s + "\n");
                    out.flush();
                    s = "";
                } catch (IOException ex)
                {
                    Logger.getLogger(OutputThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try
                {
                    synchronized(this) 
                    {
                        wait();
                    }
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(OutputThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void sendLine(String st)
    {
        OutputThread.s = st;
        synchronized(this)
        {
            notify();
        }
    }
    
    public boolean getOfferedRematch()
    {
        return offeredRematch;
    }
    
    public void setOfferedRematch(boolean choice)
    {
        offeredRematch = choice;
    }

    public boolean getOfferedUndo()
    {
        return offeredUndo;
    }
    
    public void setOfferedUndo(boolean choice)
    {
        offeredUndo = choice;
    }
    
    public boolean isDisabled()
    {
        return disabled;
    }
}
