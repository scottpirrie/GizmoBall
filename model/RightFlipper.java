package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class RightFlipper implements Flipper{

    private String type;
    private String name;
    private int xPos;
    private int yPos;
    private double xArc;
    private double yArc;
    private int rotation;
    private List<LineSegment> lines;
    private List<Circle> circles;
    private boolean isPressed;
    private double theta;
    private double xStart;
    private double yStart;

    RightFlipper(String type,String name, int xPos, int yPos){
        this.type = type;
        this.name = name;
        this.xPos = xPos + 2;
        this.yPos = yPos;
        this.xArc = xPos + 2;
        this.yArc = yPos + 2;
        this.rotation = 0;
        lines=new ArrayList<>();
        circles = new ArrayList<>();
        createLines();
        createCircles();
        this.isPressed = false;
        this.xStart = xPos; //TODO Test this
        this.yStart = yArc-yPos;
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
        //this.xPos = xPos+2; Because its a RightFlipper its on the RIGHT side of the 4x4 box

        LineSegment l1 = new LineSegment(xPos, yPos, xPos, yPos + 2);
        lines.add(l1);
    }

    @Override
    public void createCircles() {
        Circle c1 = new Circle(xPos, yPos, 0);
        Circle c2 = new Circle(xPos, yPos + 2, 0);
        circles.add(c1);
        circles.add(c2);
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
        double newDeltaX;
        double newDeltaY;

        if (!isPressed()) {
            if (theta > Math.toRadians(0)) {
                theta -= Math.toRadians(4.5);
                newDeltaX = (this.xStart * Math.cos(theta)) - (this.yStart * Math.sin(theta));
                newDeltaY = ((-1 * this.xStart) * Math.sin(theta)) + (this.yStart * Math.cos(theta));
                this.setXArc(xPos + newDeltaX);
                this.setYArc(yPos + newDeltaY);
            }
        }else {
            if(theta < Math.toRadians(90)){
                theta += Math.toRadians(4.5);
                newDeltaX = (this.xStart * Math.cos(theta)) - (this.yStart * Math.sin(theta));
                newDeltaY = ((-1 * this.xStart) * Math.sin(theta)) + (this.yStart * Math.cos(theta));
                this.setXArc(xPos+newDeltaX);
                this.setYArc(yPos+newDeltaY);
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
    public int getXPivot() {
        return xPos;
    }

    @Override
    public int getYPivot() {
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

    //Minus 2 here because we +2 at the top to push it right
    public String toString() {
        return "RightFlipper "+name+" "+(xPos-2)+" "+yPos;
    }
}
