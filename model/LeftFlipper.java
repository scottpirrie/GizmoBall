package model;

import physics.*;

import java.util.ArrayList;
import java.util.List;

public class LeftFlipper implements Flipper{

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
    private double xStart;
    private double yStart;
    private Vect pivot;


    private double moveSpeed;

    LeftFlipper(String type,String name, double xPos, double yPos){
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
        this.xStart = xPos; //TODO Test this
        this.yStart = yArc-yPos;
        theta = 18;
        thetaCheck = 0;
        pivot = new Vect(xPos+0.25,yPos+0.25);
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
        LineSegment l1 = new LineSegment(xPos, yPos+0.25, xPos, yPos + 1.75);
        LineSegment l2 = new LineSegment(xPos+0.5, yPos+0.25, xPos+0.5, yPos + 1.75);
        lines.add(l1);
        lines.add(l2);
    }

    @Override
    public void createCircles() {
        Circle c1 = new Circle(xPos+0.25, yPos+0.25, 0.25);
        Circle c2 = new Circle(xPos+0.25, yPos+1.75, 0.25);
        Circle c3 = new Circle(xPos, yPos+0.25,0);
        Circle c4 = new Circle(xPos+0.5, yPos+0.25,0);
        Circle c5 = new Circle(xPos, yPos+1.75,0);
        Circle c6 = new Circle(xPos+0.5, yPos+1.75,0);
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

    }

    @Override
    public void moveFlipper(double time) {
        if (!isPressed()) {
            if (thetaCheck > 0) {
                thetaCheck -= theta;
                for(LineSegment line: lines) {
                    line = Geometry.rotateAround(line, pivot, new Angle(-theta));
                }
                for(Circle circle: circles){
                    circle = Geometry.rotateAround(circle, pivot, new Angle(-theta));
                }
            }
        }else {
            if(thetaCheck < 90) {
                thetaCheck += theta;
                for (LineSegment line : lines) {
                    line = Geometry.rotateAround(line, pivot, new Angle(theta));
                }
                for(Circle circle: circles){
                    circle = Geometry.rotateAround(circle, pivot, new Angle(theta));
                }
            }
        }
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
    public double getXArc() {
        return xArc;
    }

    private void setXArc(double x){
        this.xArc = x;
    }

    @Override
    public double getYArc() {
        return yArc;
    }

    private void setYArc(double y){
        this.yArc = y;
    }

    public String toString() {
        return "LeftFlipper "+name+" "+xPos+" "+yPos;
    }

}
