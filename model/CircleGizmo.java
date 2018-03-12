package model;


import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CircleGizmo implements AbstractGizmo{

    private String type;
    private String name;
    private int xPos;
    private int yPos;
    private double radius;
    private List<LineSegment> lines;
    private List<physics.Circle> circles;
    private Color color;
    private boolean isTriggered;


    CircleGizmo(String type, String name,int xPos,int yPos){
        this.type = type;
        this.name = name;
        this.radius = 0.5;
        this.xPos=xPos;
        this.yPos=yPos;
        circles = new ArrayList<>();
        createCircles();
        this.color = Color.GREEN;
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

    @Override
    public int getxPos() {
        return xPos;
    }

    @Override
    public int getyPos() {
        return yPos;
    }

    @Override
    public Color getColor() {
        return color;
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

    @Override
    public void doAction() {
        Timer timer = new Timer();
        color = Color.blue;
        if(!isTriggered) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    color = Color.GREEN;
                    isTriggered = false;
                }
            }, 2000);
        }
        isTriggered = true;
    }

    public String toString() {
        return "Circle " + name + " " + xPos + " " + yPos;
    }
}
