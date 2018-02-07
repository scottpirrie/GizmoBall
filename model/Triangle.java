package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class Triangle {

    private int xPos;
    private int yPos;
    private int width;
    private List<LineSegment> lines;
    private List<Circle> circles;
    public Triangle(int xPos,int yPos,int width){
        this.xPos=xPos;
        this.yPos=yPos;
        this.width=width;
        lines=new ArrayList<>();
        circles = new ArrayList<>();

        createLines();
        createCircles();
    }

    public void createLines(){
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+width,yPos);
        LineSegment l2 = new LineSegment(xPos+width,yPos,xPos,yPos-width);
        LineSegment l3 = new LineSegment(xPos,yPos-width,xPos,yPos);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
    }
    public void createCircles(){

        Circle c1 = new Circle(xPos,yPos,0);
        Circle c2 = new Circle(xPos+width,yPos,0);
        Circle c3 = new Circle(xPos,yPos-width,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
    }
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<LineSegment> getLines() {
        return lines;
    }

    public void setLines(ArrayList<LineSegment> lines) {
        this.lines = lines;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public void setCircles(ArrayList<Circle> circles) {
        this.circles = circles;
    }

    public void rotate(){
        lines.clear();
        circles.clear();
        LineSegment l1 = new LineSegment(xPos,yPos,xPos,yPos-width);
        LineSegment l2 = new LineSegment(xPos,yPos-width,xPos+width,yPos-width);
        LineSegment l3 = new LineSegment(xPos+width,yPos-width,xPos,yPos);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        circles.add(new Circle(xPos,yPos,0));
        circles.add(new Circle(xPos,yPos-width,0));
        circles.add(new Circle(xPos+width,yPos-width,0));
    }

    public String toString(){
        return xPos+" "+yPos+" "+width;
    }
}
