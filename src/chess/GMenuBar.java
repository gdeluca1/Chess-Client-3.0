/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import chesslisteners.MenuListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Gennaro
 */
public class GMenuBar
{
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newItem;
    private JMenuItem saveItem;
    private JMenuItem colorItem;
    private JMenuItem defaultItem;
    
    public GMenuBar()
    {
       menuBar = new JMenuBar();
       
       fileMenu = new JMenu("File");
       
       newItem = new JMenuItem("New Game");
       
       saveItem = new JMenuItem("Save");
       colorItem = new JMenuItem("Change Message Color");
       
       defaultItem = new JMenuItem("Restore Defaults");
       
       newItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_N, 
                java.awt.Event.CTRL_MASK));
       
       saveItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S, 
                java.awt.Event.CTRL_MASK));
       
       colorItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_C, 
                java.awt.Event.CTRL_MASK));
       
       defaultItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_D, 
                java.awt.Event.CTRL_MASK));
       
       newItem.addActionListener(new MenuListener());
       saveItem.addActionListener(new MenuListener());
       colorItem.addActionListener(new MenuListener());
       defaultItem.addActionListener(new MenuListener());
       
       fileMenu.add(newItem);
       fileMenu.add(saveItem);
       fileMenu.add(colorItem);
       fileMenu.add(defaultItem);
       
       menuBar.add(fileMenu);
    }
    
    public JMenuBar getMenuBar()
    {
        return menuBar;
    }

    public JMenuItem getNewItem()
    {
        return newItem;
    }

    public JMenuItem getSaveItem()
    {
        return saveItem;
    }

    public JMenuItem getColorItem()
    {
        return colorItem;
    }

    public JMenuItem getDefaultItem()
    {
        return defaultItem;
    }
}
