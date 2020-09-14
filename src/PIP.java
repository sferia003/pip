/* Package */
package src;

/* Imports */
import java.awt.*;
import java.util.Random;

/**
 * Point in Polygon Solver
 * 
 * Uses raycasting to determine whether a randomly generated point
 * is inside a preset simple polygon.
 * 
 * @author Stephen Feria 
 * @version %I%, %G%
 */

 public class PIP {

    private static final double NUMERICAL_TOLERENCE = .00001; // since raycasting can not account for edges, this value determines if a point is close enough to an edge

    /**
     * 
     * @param keyPoint
     * @param upperPoint
     * @param lowerPoint
     * @return a boolean that is true if the points intersect with the side constructed from the upperPoint and lowerPoint  
     * 
     */

    public static boolean intersects(Point p, Point pU, Point pD) { 
        // point p is the point in which the ray extends (to the left), point pU is a the higher point of a side, point pD is the lower point of a side)
        if ((p.getY() > (pU.getY()) || p.getY() < pD.getY()) || p.getX() < Math.min(pU.getX(), pD.getX())) { // point p is above the highest or below the lowest point OR point p is to the left of the side, so it is out of bounds
            return false;
        } else if (Math.abs(p.getY() - pU.getY()) < NUMERICAL_TOLERENCE || Math.abs(p.getY() - pD.getY()) < NUMERICAL_TOLERENCE) {
            p.setY(p.getY() + NUMERICAL_TOLERENCE); // a ray extending from point p will land on a vertex, thus it is slightly shifted
            return intersects(p, pU, pD);
        } else { // point p is guaranteed to intercept
            return true;
        }
        
    }

    /**
     * 
     * @param point
     * @param polygon
     * @return a boolean that is true of the point is contained within the polygon
     *
     */

    public static boolean pointInPolygon(Point point,  Polygon polygon) {

        boolean bool = false; // It is assumed that it is outside (if a line is drawn to infinity and nothing is hit, it is outside)

        for (Side side : polygon.getSides()) { 
            // For every side in the polygon
            if (intersects(point, side.getPU(), side.getPD())){ // check if the points intercept 
                bool = !bool; // change the state of the boolean when they do
            }
        
        }

        return bool;

    }
}

class Point {

    private double[] point = new double[2]; // A point is an array of doubles representing cartesian coordinates (x, y)

    /**
     * Constructs a point by using randomly generated numbers from [-20, 20].
     */

    public Point() {

        Random r = new Random();

        point[0] = (r.nextInt(40) + 1) - 20;
        point[1] = (r.nextInt(40) + 1) - 20;
    }

    /**
     * 
     * @param x
     * @param y
     * 
     *  Constructs a point by using a given x and y value.
     *
     */

    public Point(double x, double y){ 
        point[0] = x;
        point[1] = y;
    }

    /**
     * @return the x value of a point
     */

    public double getX(){
        return point[0];
    }

    /**
     * @return the y value of a point
     */

    public double getY(){
        return point[1];
    }
    
    /**
     * 
     * Sets the x value of a point.
     * 
     * @param x
     *
     */

    public void setX(double x){
        point[0] = x;
    }

    /**
     * 
     * Sets the y value of a point.
     * 
     * @param y
     *
     */

    public void setY(double y){
        point[1] = y;
    }

    /**
     * 
     * @param point1
     * @param point2
     * @return the distance between point1 and point2
     *
     */
    
    public static double distance(Point p1, Point p2){

        double x1 = p1.getX(); // gets first point
        double y1 = p1.getY();

        double x2 = p1.getX(); // gets second point
        double y2 = p1.getY();

        return Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
    }

    /**
     * 
     * Draws a point relative to the middle of the screen while scaling the x and y values by mult.
     * 
     * @param g
     * @param mult
     *
     */

    public void draw(Graphics g, int mult){

        double x = point[0]*mult + 250; // scales x and y by mult and aligns it with the cartesian coordinate system
        double y = 250 - point[1]*mult;
        int r = 5;

        x = x-(r/2); // sets x relative to the middle of the screen taking the radius into account
        y = y-(r/2);

        g.fillOval((int) x, (int) y,r,r);
    }

    /**
     * 
     * @return Returns a string that represents a point in the console
     *
     */

    @Override
    public String toString() { 
        return String.format("(%.0f, %.0f)", point[0], point[1]); 
    } 
}


class Polygon {
    Side[] sides; // A polygon is an array of sides

    /**
     * Constructs a polygon by using an array of points
     * 
     * @param points
     * 
     */

    public Polygon(Point[] points) {
        constructSides(points);
    }

    /**
     * @return the sides in a polygon
     */

    public Side[] getSides() {
        return sides;
    }

    /**
     * 
     * Constructs sides when given an array of points
     * 
     * @param points
     * 
     */

    private void constructSides(Point[] points) {

        sides = new Side[(points.length)]; // initializes sides

        for (int i = 0; i < points.length; i++) {

            if (i == (points.length-1)) {  // if on the last point
                sides[i] = new Side(points[0], points[i]);
                break;
            }

            sides[i] = new Side(points[i], points[i+1]);
        }
    }

    /**
     * 
     * @return Returns a string that represents a polygon in the console
     *
     */

    @Override
    public String toString() { 

        String representation = ""; // initializes the string representation

        for (int i = 0; i < sides.length; i++) { 

            if (i == sides.length - 1){ // if on the last point
                representation = representation + sides[i].toString();
                break;
            }

            representation = representation + sides[i].toString() + ", ";

        }

        return String.format("[ %s ]", representation); 
    } 

    /**
     * 
     * Draws a polygon and scales it via mult.
     * 
     * @param g
     * @param mult
     *
     */

    public void draw(Graphics g, int mult) {

        for (Side side : sides) { // for every side in sides
            side.draw(g, mult); // draw the side
        }

        sides[sides.length - 1].getPU().draw(g, mult); // draws the final point in the polygon
        
    }
}

class Side {

    Point pU; // upper point
    Point pD; // lower point

    /**
     * Constructs a side by using two points
     * 
     * @param p1
     * @param p2
     * 
     */

    public Side(Point p, Point p2) {

        if (p.getY() > p2.getY()){ // determines which point will be upper
            pU = p;
            pD = p2;
        } else {
            pU = p2;
            pD = p;
        }

    }

    /**
     * @return the upper point
     */

    public Point getPU() {
        return pU;
    }

    /**
     * @return the lower point
     */

    public Point getPD() {
        return pD;
    }

    /**
     * 
     * Draws a side while accounting for scaling factor (mult)
     * 
     * @param g
     * @param mult
     *
     */


    public void draw(Graphics g, int mult) {

        int x1 = (int) pU.getX()*mult + 250; // gets first point
        int y1 = 250 - (int) pU.getY()*mult;

        int x2 = (int) pD.getX()*mult + 250; // gets last point
        int y2 = 250 - (int) pD.getY()*mult;

        pU.draw(g, mult); // draws the points
        pD.draw(g, mult);
        g.drawLine(x1, y1, x2, y2); // draws a line connecting the points
    }

    /**
     * 
     * @return Returns a string that represents a side in the console
     *
     */

    @Override
    public String toString() { 
        return String.format("{%s, %s}", pD.toString(), pU.toString()); 
    } 
}

 