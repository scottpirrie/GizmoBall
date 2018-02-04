package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private int xPos;
    private int yPos;
    private int width;
    private List<LineSegment> lines;
    private List<Circle> circles;
    public Square(int xPos,int yPos,int width){
        this.xPos=xPos;
        this.yPos=yPos;
        this.width=width;
        lines=new ArrayList<>();
        circles=new ArrayList<>();
        createLines();
        createCircles();

    }


    public List<LineSegment> getLines(){
        return lines;
    }

    public List<Circle> getCircles() {
        return circles;
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

    public void createCircles(){
        physics.Circle c1 = new physics.Circle(xPos,yPos,0);
        physics.Circle c2 = new physics.Circle(xPos+width,yPos,0);
        physics.Circle c3 = new physics.Circle(xPos+width,yPos+width,0);
        physics.Circle c4 = new physics.Circle(xPos,yPos+width,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
    }
    public void createLines(){
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+width,yPos);
        LineSegment l2 = new LineSegment(xPos+width,yPos,xPos+width,yPos+width);
        LineSegment l3 = new LineSegment(xPos+width,yPos+width,xPos,yPos+width);
        LineSegment l4 = new LineSegment(xPos,yPos+width,xPos,yPos);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
    }
}
