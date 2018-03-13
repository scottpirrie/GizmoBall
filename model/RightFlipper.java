package model;

import physics.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RightFlipper implements Flipper{

    private String type;
    private String name;
    private double xPos;
    private double yPos;
    private double xArc;
    private double yArc;
    private int rotation;
    private List<LineSegment> lines;
    private List<Circle> circles;
    private boolean isPressed;
    private double theta;
    private double thetaCheck;
    private Vect pivot;

    RightFlipper(String type,String name, double xPos, double yPos){
        this.type = type;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.xArc = xPos;
        this.yArc = yPos + 2;
        this.rotation = 0;
        lines=new ArrayList<>();
        circles = new ArrayList<>();
        createLines();
        createCircles();
        this.isPressed = false;
        theta = 18;
        thetaCheck = 0;
        pivot = new Vect(xPos+1.75,yPos+0.25);
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
    public void createLines() {
        //this.xPos = xPos+2; Because its a RightFlipper its on the RIGHT side of the 4x4 box

        LineSegment l1 = new LineSegment(xPos+1.5, yPos+0.25, xPos+1.5, yPos + 1.75);
        LineSegment l2 = new LineSegment(xPos+2, yPos+0.25, xPos+2, yPos + 1.75);
        lines.add(l1);
        lines.add(l2);
    }

    @Override
    public void createCircles() {
        Circle c1 = new Circle(xPos+1.75, yPos+0.25, 0.25);
        Circle c2 = new Circle(xPos+1.75, yPos+1.75, 0.25);
        Circle c3 = new Circle(xPos+1.5, yPos+0.25,0);
        Circle c4 = new Circle(xPos+2, yPos+0.25,0);
        Circle c5 = new Circle(xPos+1.5, yPos+1.75,0);
        Circle c6 = new Circle(xPos+2, yPos+1.75,0);
        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
        circles.add(c5);
        circles.add(c6);
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
        Vect rotatePivot = new Vect(xPos+1,yPos+1);
        for(int x=0; x<circles.size();x++){
            circles.set(x,Geometry.rotateAround(circles.get(x),rotatePivot,new Angle(Math.toRadians(90))));
        }
        for(int x=0; x<lines.size(); x++){
            lines.set(x,Geometry.rotateAround(lines.get(x),rotatePivot,new Angle(Math.toRadians(90))));
        }
        pivot = Geometry.rotateAround(pivot,rotatePivot,new Angle(Math.toRadians(90)));
    }

    @Override
    public void moveFlipper(double time) {
        if (!isPressed()) {
            if (thetaCheck > 0) {
                thetaCheck -= theta;
                for(LineSegment line: lines) {
                    LineSegment newline = Geometry.rotateAround(line, pivot, new Angle(Math.toRadians(-theta)));
                    lines.set(lines.indexOf(line),newline);
                }
                for(Circle circle: circles){
                    Circle newCircle = Geometry.rotateAround(circle, pivot, new Angle(Math.toRadians(-theta)));
                    circles.set(circles.indexOf(circle),newCircle);
                }
            }
        }else {
            if(thetaCheck < 90) {
                thetaCheck += theta;
                for (LineSegment line : lines) {
                    LineSegment newline = Geometry.rotateAround(line, pivot, new Angle(Math.toRadians(theta)));
                    lines.set(lines.indexOf(line),newline);
                }

                for(Circle circle: circles){
                    Circle newCircle = Geometry.rotateAround(circle, pivot, new Angle(Math.toRadians(theta)));
                    circles.set(circles.indexOf(circle),newCircle);
                }
            }
        }
    }

    public double getThetaCheck(){
        return thetaCheck;
    }

    @Override
    public void setPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public double getXPivot() {
        return xPos;
    }

    @Override
    public double getYPivot() {
        return yPos;
    }

    @Override
    public void doAction() {
        Timer timer = new Timer();
        if(!isPressed) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    while(thetaCheck < 90) {
                        moveFlipper(0.017);
                    }
                    isPressed = false;
                }
            }, 1000);
        }
        isPressed = true;
    }

    //Minus 2 here because we +2 at the top to push it right
    public String toString() {
        return "RightFlipper "+name+" "+(xPos-2)+" "+yPos;
    }
}
