/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chessmaps;

import java.awt.Color;

/**
 *
 * @author Gennaro
 */
public class ColorMap {
    
    public final static Color BLACK = Color.LIGHT_GRAY;
    public final static Color WHITE = Color.WHITE;
    public final static Color SELECTED = new Color(0x10, 0xBB, 0xE6); // Selected must be different from the others for resetting the board.
    public final static Color FROM = new Color(0x19, 0x75, 0xD1);
    public final static Color TO =  new Color(0x0F, 0xAC, 0xD4);
}
