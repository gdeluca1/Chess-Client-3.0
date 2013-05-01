/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messaging;

import chesslisteners.MoveListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gennaro
 */
public class Messenger
{
    private final static int PORT = 6790;
    private static String IP_ADDRESS;
    private static Socket socket;
    
    private static OutputThread out;
    
    public Messenger()
    {
        IP_ADDRESS = MoveListener.getIP();
        
        
        while(socket == null)
        {
            try
            {
                socket = new Socket(IP_ADDRESS, PORT);
            } 
            catch (UnknownHostException ex)
            {
                Logger.getLogger(Messenger.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (IOException ex)
            {
                Logger.getLogger(Messenger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public void start()
    {
        InputThread in = new InputThread(socket);
        in.start();
        System.out.println("In started.");
        out = new OutputThread(socket);
        out.start();
        System.out.println("Out started.");
    }
    
    public static OutputThread getOut()
    {
        return out;
    }     
}

