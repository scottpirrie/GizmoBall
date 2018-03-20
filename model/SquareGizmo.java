package model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SquareGizmo implements  AbstractGizmo {

    private final String type;
    private final String name;
    private double xPos;
    private double yPos;
    private final List<LineSegment> lines;
    private final List<Circle> circles;
    private Color color;
    private boolean isTriggered;

    SquareGizmo(String type, String name, double xPos, double yPos){
        this.type = type;
        this.name = name;
        this.xPos=xPos;
        this.yPos=yPos;
        lines=new ArrayList<>();
        circles=new ArrayList<>();
        createLines(this.xPos,this.yPos);
        createCircles(this.xPos,this.yPos);
        this.color = Color.RED;
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
        return 0;
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

    private void createLines(double xPos,double yPos) {
        lines.clear();
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+1,yPos);
        LineSegment l2 = new LineSegment(xPos+1,yPos,xPos+1,yPos+1);
        LineSegment l3 = new LineSegment(xPos+1,yPos+1,xPos,yPos+1);
        LineSegment l4 = new LineSegment(xPos,yPos+1,xPos,yPos);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
    }

    private void createCircles(double xPos,double yPos) {
        circles.clear();
        physics.Circle c1 = new physics.Circle(xPos,yPos,0);
        physics.Circle c2 = new physics.Circle(xPos+1,yPos,0);
        physics.Circle c3 = new physics.Circle(xPos+1,yPos+1,0);
        physics.Circle c4 = new physics.Circle(xPos,yPos+1,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
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

    }

    @Override
    public void doAction() {

        Timer timer = new Timer();
        color = Color.GREEN;
        if(!isTriggered) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    color = Color.RED;
                    isTriggered = false;
                }
            }, 2000);
        }
        isTriggered = true;
    }

    @Override
    public void move(double x, double y) {
        xPos = x;
        yPos = y;
        lines.clear();
        circles.clear();
        createLines(x,y);
        createCircles(x,y);
    }

    public String toString(){
        return "Square "+name+" "+xPos+" "+yPos;
    }

}
