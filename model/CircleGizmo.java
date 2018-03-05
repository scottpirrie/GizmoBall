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
    private int radius;
    private List<LineSegment> lines;
    private List<physics.Circle> circles;


    public CircleGizmo(String type, String name,int xPos,int yPos,int radius){
        this.type = type;
        this.name = name;
        this.radius=radius;
        this.xPos=xPos;
        this.yPos=yPos;
        circles = new ArrayList<>();

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
    public void createLines() {
        //DOES NOTHING
    }

    @Override
    public void createCircles() {
        physics.Circle circle = new physics.Circle(xPos,yPos,radius);
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
