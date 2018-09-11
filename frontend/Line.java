package CatanAI.frontend;

import processing.core.PApplet;

public class Line {
    /* Color Codes
     *  0 = black
     *  1 = blue
     *  2 = red
     *  3 = orange
     *  4 = white (will probably change this)
     */

    private int x1, y1, x2, y2;
    private int color = 0;

    private PApplet myPApplet;
    private Point p1, p2;


    public Line(Point p1, Point p2, PApplet myPApplet){
        this.p1 = p1;
        this.p2 = p2;
        this.myPApplet = myPApplet;
    }

    public Point getP1(){
        return p1;
    }

    public Point getP2(){
        return p2;
    }

    public int getColor(){
        return color;
    }

    public void setColor(int newColor){
        color = newColor;
    }

    public void display(){
        myPApplet.stroke(0);
        myPApplet.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
