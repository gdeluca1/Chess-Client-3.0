/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;

/**
 *
 * @author Gennaro
 */
public class ChessSquare {
    private int xPos;
    private int yPos;
    private Color color;
    private Color originalColor;
    private boolean firstTime = true;
    
    public ChessSquare(int xPos, int yPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    @Override
    public String toString()
    {
        return "xPos: " + xPos + " yPos: " + yPos;
    }            
    
    public int getXPos()
    {
        return xPos;
    }
    
    public int getYPos()
    {
        return yPos;
    }
    
    public void setColor(Color color)
    {
        if(firstTime) {
            originalColor = color;
            firstTime = false;
        }
        this.color = color;
    }
    
    public void resetColor()
    {
        color = originalColor;
    }
    
    public Color getColor()
    {
        return color;
    }
    
    public Color getOriginalColor()
    {
        return originalColor;
    }
    
    /**
     * Checks to see if a specified point is inside that square.
     * @param xPos
     * @param yPos
     * @return 
     */
    public boolean contains(int inputX, int inputY, int panelWidth, int panelHeight)
    {
        int xMargin = panelWidth/8;
        int yMargin = panelHeight/8;
        int xPoint = this.xPos * panelWidth/8;
        int yPoint = this.yPos * panelHeight/8;
        boolean containsX = (xPoint <= inputX) && (xPoint + xMargin >= inputX);
        boolean containsY = (yPoint <= inputY) && (yPoint + yMargin >= inputY);
        return (containsX && containsY);
    }
}
