package model;

import physics.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TriangleGizmo implements AbstractGizmo{


    private String type;
    private String name;
    private double xPos;
    private double yPos;
    private int rotation;
    private List<LineSegment> lines;
    private List<Circle> circles;
    private Color color;
    private boolean isTriggered;

    TriangleGizmo(String type, String name,double xPos,double yPos){
        this.type = type;
        this.name = name;
        this.xPos=xPos;
        this.yPos=yPos;
        this.rotation = 0;
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        createLines();
        createCircles();
        this.color = Color.YELLOW;
        this.isTriggered = false;
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRotation() {
        return rotation;
    }

    @Override
    public double getxPos() {
        return xPos;
    }

    @Override
    public double getyPos() {
        return yPos;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void createLines() {
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+1,yPos);
        LineSegment l2 = new LineSegment(xPos,yPos+1,xPos,yPos);
        LineSegment l3 = new LineSegment(xPos+1,yPos,xPos,yPos+1);


        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
    }

    @Override
    public void createCircles() {
        Circle c1 = new Circle(xPos,yPos,0);
        Circle c2 = new Circle(xPos+1,yPos,0);
        Circle c3 = new Circle(xPos,yPos+1,0);


        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
    }

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
        Vect pivot = new Vect(xPos+0.5,yPos+0.5);
        for(int x=0; x<circles.size();x++){
            circles.set(x,Geometry.rotateAround(circles.get(x),pivot,new Angle(Math.toRadians(90))));
        }

        for(int x=0; x<lines.size(); x++){
            lines.set(x,Geometry.rotateAround(lines.get(x),pivot,new Angle(Math.toRadians(90))));
        }


    }

    @Override
    public void doAction() {

        Timer timer = new Timer();
        color = Color.CYAN;
        if(!isTriggered) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    color = Color.YELLOW;
                    isTriggered = false;
                }
            }, 2000);
        }
        isTriggered = true;
    }

    @Override
    public void move(double x, double y) {
        xPos=x;
        yPos=y;
    }

    @Override
    public String toString() {
        return "Triangle "+name+" "+xPos+" "+yPos;
    }

}
