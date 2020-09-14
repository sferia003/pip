/* Package */
package src;

/* Imports */
import javax.swing.*;
import java.awt.*;

/**
 * Graphical Interface
 *
 * Uses the java.swing and java.awt to visually display results of
 * the Point In Polygon Solver.
 *
 * @author Stephen Feria
 * @version %I%, %G%
 *
 */

public class GUI extends JFrame {

    private static final int MULTIPLIER = 10; // this is the multiplier that determines the scale of the points {Example (1, 2) -> (10,20) [Each unit represents 10 pixels]}

    GUI() { // sets some parameters of the GUI
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) { // creates the GUI and paints the GUI

        GUI m = new GUI();
        m.repaint(); 

    }

    /**
     * Generates a random point and constructs a simple polygon. 
     * The point turns green if it is inside, and red if it is outside the shape.
     * The point's color is determined by the result of the Point in Polygon Solver.
     * 
     * @param graphics 
     */

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.BLACK); // draws two lines to gauge a sense of where the shape and point are located relative to a coordinate plane.
        g.drawLine(250, 500, 250, 0);
        g.drawLine(0, 250, 500, 250);
        
        Point p = new Point(2,2); // generates a new point

        g.setColor(Color.BLUE); // sets the polygon's color to blue
        Point[] polyPoints = new Point[7]; // constructs the structure for the polygon by placing specific points into an array 
            polyPoints[0] = new Point(-5,-1);
            polyPoints[1] = new Point(0,0);
            polyPoints[2] = new Point(1,-3);
            polyPoints[3] = new Point(0,-15);
            polyPoints[4] = new Point(3,-4);
            polyPoints[5] = new Point(9,-2);
            polyPoints[6] = new Point(10,10);

        Polygon polygon = new Polygon(polyPoints); // constructs a simple polygon by utilizing the array of points

        polygon.draw(g, MULTIPLIER); // draws the polygon

        if (PIP.pointInPolygon(p, polygon)) { // changes the point color depending on the boolean value returned from the Point in Polygon Solver
            g.setColor(Color.GREEN); // sets the point green (if inside the polygon) and draws it
            p.draw(g, MULTIPLIER);
        } else {
            g.setColor(Color.RED); // sets the point red (if outside the polygon) and draws it
            p.draw(g, MULTIPLIER);
        }
    }
}