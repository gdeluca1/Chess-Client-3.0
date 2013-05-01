package chess;

import chesslisteners.MoveListener;
import chesslisteners.ResizeListener;
import chessmaps.ChessPlayer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import messaging.EnterListener;
import messaging.Messenger;
import messaging.SendButtonListener;

/**
 *
 * @author Gennaro
 */
public class Chess implements ActionListener 
{

    private final static String GAME_NAME = "Chess";
    private static ChessBoard chessBoard = null;
    private static JTextPane textPane;
    private static JTextArea inputArea;
    private static JFrame frame;
    private final static ChessPlayer PLAYER_COLOR = ChessPlayer.white;
    private final static Chess chessListener = new Chess();
    private static JButton redrawButton, sendButton, clearButton;
    private static MoveListener moveListener;
    private static GMenuBar menuBar;
    
    private final static double VERSION = 2.1;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try { 
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Chess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Console.prepareTextPane();
        
        textPane = new JTextPane(Console.document);
        inputArea = new JTextArea("Type your message here...");
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        
        InputMap inputMap = inputArea.getInputMap();
        ActionMap actionMap = inputArea.getActionMap();

        Object transferTextActionKey = "TRANSFER_TEXT";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),transferTextActionKey);
        
        //Also add shift+enter binding here if you want
        actionMap.put(transferTextActionKey, new EnterListener());
        
        inputArea.requestFocus();
        
        chessBoard = new ChessBoard(PLAYER_COLOR);
        
        // Must be instantiated after prepare instance was called in the ChessBoard constructor.
        moveListener = new MoveListener();
        
        // Must be instantiated after moveListener.
        Messenger messenger = new Messenger();
        messenger.start();
        
        frame = new JFrame(GAME_NAME);
        
        menuBar = new GMenuBar();
        frame.setJMenuBar(menuBar.getMenuBar());
        
        try
        {
            Image whiteKingImage = ImageIO.read(Chess.class.getResource("/pieces/whiteKing.png"));
            frame.setIconImage(whiteKingImage);
        } 
        catch (IOException ex)
        {
            System.out.println("Error in creating icon for frame.");
            Logger.getLogger(Chess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        chessBoard.addMouseListener(moveListener);
        
        chessBoard.addComponentListener(new ResizeListener());
        chessBoard.setSize(700, 700);
        chessBoard.setPreferredSize(new Dimension(700, 700));
        
        JPanel userPanel = new JPanel();
        
        textPane.setEditable(false);
        
        JPanel southPanel = new JPanel();
        redrawButton = new JButton("Redraw");
        redrawButton.addActionListener(chessListener);
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(chessListener);
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(new SendButtonListener());
        
        southPanel.setLayout(new GridLayout(5, 1));
        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        buttonPanel1.add(redrawButton);
        buttonPanel1.add(clearButton);
        buttonPanel2.add(sendButton);
        
        JScrollPane textPanel = new JScrollPane(inputArea);
        
        southPanel.add(buttonPanel1);
        southPanel.add(textPanel);
        southPanel.add(buttonPanel2);
        
        JPanel noWrapPanel = new JPanel(new BorderLayout());
        noWrapPanel.add(textPane);
        JScrollPane scrollPane = new JScrollPane(textPane);
        
        userPanel.setLayout(new GridLayout(2, 1));
        userPanel.add(scrollPane);
        userPanel.add(southPanel);
        
        // Allow hotkey access to menu bar even when on user panel.
        userPanel.addMouseListener(moveListener);
        
        JSplitPane sPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chessBoard, userPanel);
        sPane.setDividerLocation(700);
        frame.add(sPane);
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = frame.getSize().width;
        int h = frame.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        frame.setLocation(x, y);
        if(ChessPieceBox.getInstance().getPlayer() == ChessPlayer.black)
        {
            moveListener.startThread();
        }
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Console.finalizeMessageColor();
            }
        });
    }
    
    public boolean isFocusable()
    {
        return true;
    }
    
    public static void scrollDown()
    {
        int x;
        textPane.selectAll();
        x = textPane.getSelectionEnd();
        textPane.select(x, x);
    }
    
    public static ChessBoard getChessBoard()
    {
        return chessBoard;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == redrawButton)
        {
            chessBoard.repaint();
            Console.out.println("Repainted.");
        }
        else if(e.getSource() == clearButton)
        {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear your messages?", null, 
                       JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
               
            if(choice != JOptionPane.YES_OPTION)
            {
                return;
            }
            textPane.setText("");
        }
    }

    public static MoveListener getMoveListener()
    {
        return moveListener;
    }
    
    public static JTextArea getInputArea()
    {
        return inputArea;
    }
    
    public static double getVersionNumber()
    {
        return VERSION;
    }
    
    public static GMenuBar getGMenuBar()
    {
        return menuBar;
    }

    public static ChessPlayer getPlayerColor()
    {
        return PLAYER_COLOR;
    }
}
