/*
 * TCSS 305 - Tetris
 */

package controls;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.Point;
import model.TetrisPiece;


/**
 * A helper class to paint the next Tetris piece.
 * 
 * @author Hop Pham
 * @version Feb 28 2018
 */
public class NextPiecePanel extends JPanel {

    /**  A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 5944162416430906760L;

    /** The panel ratio related to I. */
    private static final int I_RATIO = 4;
    
    /** The panel ratio related to any Tetrispiece excepted the I one. */
    private static final int RATIO = 3;
    
    /** The background color of the panel. */
    private static final Color BACKGROUND_COLOR = Color.GRAY;

    /** The color to paint with. */
    private static final Color FOREGROUND_COLOR = Color.WHITE;

    /** The width - height of one square piece. */
    private static final int ONE_EDGE_SIZE = 40;

    /** To flip the tetris coordinate. */
    private static final int FLIP = 3;

    

    // Instance Fields
    
    /** List of base rectangle which will build the Tetris. */
    private final List<Rectangle2D> myShapes = new ArrayList<>();

    /**
     * Constructs a new game panel.
     */
    public NextPiecePanel() {
        super();
        setPreferredSize(new Dimension(ONE_EDGE_SIZE, ONE_EDGE_SIZE));
        setBackground(BACKGROUND_COLOR);

    }

    /**
     * Paints the current path.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
      //without call super that will draw all, otherwise draw only new one
      //make a list to draw
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setPaint(FOREGROUND_COLOR);

        for (final Rectangle2D rec:myShapes) {
            g2d.fill(rec);            
        }
        
        for (final Rectangle2D rec:myShapes) {            
            g2d.setPaint(Color.BLACK);
            g2d.draw(rec);           
        }
        
        
    } 
    
    /**
     * Repaint the tetris piece at new position.
     * @param theArgument the tetris piece
     */
    public void nextPiece(final TetrisPiece theArgument) {      
        final Point[] point = ((TetrisPiece) theArgument).getPoints();
        int scale = RATIO;
        int heightScale = 1;
        if (((TetrisPiece) theArgument).name().equals("I")
                        || ((TetrisPiece) theArgument).name().equals("O")) {
            scale = I_RATIO;
            heightScale = 0;
        }
        setPreferredSize(new Dimension(ONE_EDGE_SIZE * scale, ONE_EDGE_SIZE * RATIO));
        buildPiece(point, heightScale);
        repaint();
    }
    
    /**
     * Helper method to fill the TetrisPiece.
     * @param thePoint the TetrisPiece
     * @param theScale the scale
     */
    private void buildPiece(final Point[] thePoint, final int theScale) {
        myShapes.clear();
        for (int i = 0; i < thePoint.length; i++) {
            final int x = thePoint[i].x();
            final int y = thePoint[i].y();
            myShapes.add(new Rectangle2D.Double(x * ONE_EDGE_SIZE,
                                                (FLIP - y - theScale) * ONE_EDGE_SIZE,
                                                ONE_EDGE_SIZE, ONE_EDGE_SIZE));
        }
    }

}
