package CatanAI.frontend;

import processing.core.PApplet;

public class Line {
    /* Color Codes
     *  0 = Red
     *  1 = Blue
     *  2 = Orange
     *  3 = Green
     *  else = black
     *
     */

    private int color = 4; // defaults to black

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
        switch(color){
            case 0: myPApplet.stroke(255, 0, 0); // Red
                    myPApplet.strokeWeight(4);
                    break;
            case 1: myPApplet.stroke(0, 0, 255); // Blue
                    myPApplet.strokeWeight(4);
                    break;
            case 2: myPApplet.stroke(244, 149, 66); // Orange
                    myPApplet.strokeWeight(4);
                    break;
            case 3: myPApplet.stroke(34, 139, 34); // Green
                    myPApplet.strokeWeight(4);
                    break;
            default: myPApplet.stroke(0); // Black
                    break;

        }
        myPApplet.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        myPApplet.fill(0);
        myPApplet.stroke(0);
        myPApplet.strokeWeight(1);
    }
}
