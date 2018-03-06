package model;


import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class CircleGizmo implements AbstractGizmo{

    private String type;
    private String name;
    private int xPos;
    private int yPos;
    private double radius;
    private List<LineSegment> lines;
    private List<physics.Circle> circles;


    CircleGizmo(String type, String name,int xPos,int yPos){
        this.type = type;
        this.name = name;
        this.radius = 0.5;
        this.xPos=xPos;
        this.yPos=yPos;
        circles = new ArrayList<>();
        createCircles();
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getxPos() {
        return xPos;
    }

    @Override
    public int getyPos() {
        return yPos;
    }

    @Override
    public void createLines() {
        //DOES NOTHING
    }

    @Override
    public void createCircles() {
        physics.Circle circle = new physics.Circle(xPos+radius,yPos+radius,radius);
        circles.add(circle);
    }

    //Returns an empty list
    @Override
    public List<LineSegment> getLines() {
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        return circles;
    }

    @Override
    public void rotate() {
        //DOES NOTHING
    }

    public String toString() {
        return "Circle " + name + " " + xPos + " " + yPos;
    }
}
