/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Gennaro
 */
public abstract class Console 
{
    private static StyleContext context;
    public static StyledDocument document;
    private static Style normalStyle, errorStyle, adminStyle;
    private static Color messageColor = null;// = new Color(0x008000);
    private static File file;
    
    public static void prepareTextPane()
    {
        context = new StyleContext();
        document = new DefaultStyledDocument(context);
        adminStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
        errorStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
        normalStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
        normalStyle.addAttribute(StyleConstants.Foreground, Color.BLACK);
        errorStyle.addAttribute(StyleConstants.Foreground, Color.RED);
        adminStyle.addAttribute(StyleConstants.Foreground, Color.BLUE);
        
        checkMessageColor();
    }
    
    /**
     * Normal is 1 if the text is normal text, 2 if the text
     * is a warning or error, and 3 if it is an admin message.
     * @param s
     * @param isNormal 
     */
    private static synchronized void println(String s, int color)
    {
        if(messageColor == null)
        {
            messageColor = new Color(0x008000);
        }
        
        final String string = s + "\n";
        
        SwingWorker worker = new Console.TextWorker(color, string);
        
        worker.execute();
    }
    
    public static class out 
    {
        public static void println(Object s)
        {
            Console.println(s.toString(), 1);
            //Console.println("", 1);
        } 
    }
    
    public static class err 
    {
        public static void println(Object s)
        {
            Console.println(s.toString(), 2);
            //Console.println("", 2);
        }
    }
    
    public static class admin
    {
        public static void println(Object s)
        {
            Console.println(s.toString(), 3);
            //Console.println("", 3);
        }
    }
    
    public static class message
    {
        public static void println(Object s)
        {
            Console.println(s.toString(), 4);
            //Console.println("", 4);
        }
    }
    
    private static class TextWorker extends SwingWorker<Void, Void>
    {
        int color;
        String string;
        public TextWorker(int color, String string)
        {
            super();
            this.color = color;
            this.string = string;
        }
        @Override
        public Void doInBackground()
        {
            if(color == 1) 
            {
                StyleConstants.setForeground(errorStyle, Color.BLACK);
                try {
                    document.insertString(document.getLength(), string, normalStyle);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(color == 2) 
            {
                StyleConstants.setForeground(errorStyle, Color.RED);
                try {
                    document.insertString(document.getLength(), string, errorStyle);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            else if(color == 3)
            {
                StyleConstants.setForeground(adminStyle, Color.BLUE);
                try {
                    document.insertString(document.getLength(), string, adminStyle);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(color == 4)
            {
                StyleConstants.setForeground(adminStyle, messageColor);//0x008000));
                try {
                    document.insertString(document.getLength(), string, adminStyle);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return null; 
        }

        @Override
        public void done()
        {
            Chess.scrollDown();
        }
    }
    
    private static void checkMessageColor()
    {
        File userHomeDirectory = new File(System.getProperty("user.home"));
        file = new File(userHomeDirectory, "GennaroChessConfig.dat");
        boolean createdFile = true;
        try
        {
            createdFile = file.createNewFile();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(createdFile)
        {
            return;
        }
        ObjectInputStream in = null;
        try
        {
            FileInputStream fr = new FileInputStream(file);
            try
            {
               in = new ObjectInputStream(fr); 
            }
            catch(EOFException ex)
            {
                System.out.println("No information found.");
                return;
            }
            
            Object inputColorO = in.readObject();
            if(inputColorO != null)
            {
                try
                {
                    Color inputColor = (Color) inputColorO;
                    messageColor = inputColor; //new Color(Integer.parseInt(colorName));
                    System.out.println("Message color set to: " + messageColor);
                }
                catch(Exception e)
                {
                    System.out.println("Error in reading color name.");
                    // Don't do anything.
                }
            }
        } 
        catch(ClassNotFoundException | IOException ex)
        {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                if(in != null)
                {
                    in.close();
                }
            } catch (IOException ex)
            {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void finalizeMessageColor()
    {
        ObjectOutputStream out = null;
        try
        {
            FileOutputStream fw = new FileOutputStream(file);
            out = new ObjectOutputStream(fw);
            System.out.println(messageColor);
            out.writeObject(messageColor);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if(out != null)
            {
                try
                {
                    out.close();
                } 
                catch (IOException ex)
                {
                    Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void resetMessageColor()
    {
        messageColor = new Color(0x008000);
    }
    
    public static Color getMessageColor()
    {
        return messageColor;
    }
    
    public static void setMessageColor(Color color)
    {
        messageColor = color;
    }
}
