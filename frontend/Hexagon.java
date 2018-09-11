package frontend;

import processing.core.PApplet;

public class Hexagon {

    private int centerX, centerY;
    private static int RADIUS = 80;
    private PApplet myPApplet;
    private Line[] edges = new Line[6];
    private Point[] points = new Point[6];


    /* ResourceType Codes
     *  0 = desert
     *  1 = wood
     *  2 = clay
     *  3 = sheep
     *  4 = stone
     *  5 = hay
     */
    private int resourceType = 0;

    public Hexagon(int centerX, int centerY, PApplet myPApplet){
        this.centerX = centerX;
        this.centerY = centerY;
        this.myPApplet = myPApplet;
        initPoints();
        initLines();
    }

    private void initPoints(){
        points[0] = new Point(centerX - RADIUS, centerY - (RADIUS/2)); // NW point
        points[1] = new Point(centerX, centerY - RADIUS); // N point
        points[2] = new Point(centerX + RADIUS, centerY - (RADIUS/2)); // NE point
        points[3] = new Point(centerX + RADIUS, centerY + (RADIUS/2)); // SE point
        points[4] = new Point(centerX, centerY + RADIUS); // S point
        points[5] = new Point(centerX - RADIUS, centerY + (RADIUS/2)); // SW point
    }

    private void initLines(){
        edges[0] = new Line(points[0], points[1], myPApplet);
        edges[1] = new Line(points[1], points[2], myPApplet);
        edges[2] = new Line(points[2], points[3], myPApplet);
        edges[3] = new Line(points[3], points[4], myPApplet);
        edges[4] = new Line(points[4], points[5], myPApplet);
        edges[5] = new Line(points[5], points[0], myPApplet);
    }

    public int getCenterX(){
        return centerX;
    }

    public int getCenterY(){
        return centerY;
    }

    public Line getLine(int lineNumber){
        return edges[lineNumber];
    }

    public Line[] getEdges(){
        return edges;
    }

    public Point[] getPoints(){
        return points;
    }

    public void display(){
        for( Line l : edges){
            l.display();
        }
    }
}
