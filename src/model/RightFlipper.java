package src.model;

import src.physics.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RightFlipper implements Flipper{

    private final String type;
    private final String name;
    private double xPos;
    private double yPos;
    private int rotation;
    private final List<LineSegment> lines;
    private final List<Circle> circles;
    private boolean isPressed;
    private double theta;
    private double angularVelo;
    private Vect pivot;

    public RightFlipper(String type,String name, double xPos, double yPos){
        this.type = type;
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.rotation = 0;
        lines=new ArrayList<>();
        circles = new ArrayList<>();
        createLines(xPos,yPos);
        createCircles(xPos,yPos);
        this.isPressed = false;
        angularVelo = 1080;
        theta = 90;
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

    private void createLines(double xPos,double yPos) {
        LineSegment l1 = new LineSegment(xPos+1.5, yPos+0.25, xPos+1.5, yPos + 1.75);
        LineSegment l2 = new LineSegment(xPos+2, yPos+0.25, xPos+2, yPos + 1.75);
        lines.add(l1);
        lines.add(l2);
    }

    private void createCircles(double xPos,double yPos) {
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
        if(rotation < 4){
            rotation++;
        }else{
            rotation = 0;
        }

        Vect rotatePivot = new Vect(xPos+1,yPos+1);
        for(int x=0; x<circles.size();x++){
            circles.set(x, Geometry.rotateAround(circles.get(x),rotatePivot,new Angle(Math.toRadians(90))));
        }
        for(int x=0; x<lines.size(); x++){
            lines.set(x,Geometry.rotateAround(lines.get(x),rotatePivot,new Angle(Math.toRadians(90))));
        }
        pivot = Geometry.rotateAround(pivot,rotatePivot,new Angle(Math.toRadians(90)));
    }

    @Override
    public void moveFlipper(double time) {
        if (!isPressed()) {
            if (theta < 90) {
                if(angularVelo < 0){
                    angularVelo = -angularVelo;
                }

                theta = theta + (angularVelo * time);

                if(theta > 90){
                    theta = 90;
                }

                for (int i = 0; i < lines.size(); i++) {
                    LineSegment newline = Geometry.rotateAround(lines.get(i), pivot, new Angle(Math.toRadians(-angularVelo / (1 / time))));
                    lines.set(i, newline);
                }

                for (int i = 0; i < circles.size(); i++) {
                    Circle newCircle = Geometry.rotateAround(circles.get(i), pivot, new Angle(Math.toRadians(-angularVelo / (1 / time))));
                    circles.set(i, newCircle);
                }
            }
        }else if(isPressed()){
            if(theta > 0) {
                if(angularVelo > 0){
                    angularVelo = -angularVelo;
                }

                theta = theta + (angularVelo * time);

                if(theta < 0){
                    theta = 0;
                }

                for (int i = 0; i < lines.size(); i++) {
                    LineSegment newline = Geometry.rotateAround(lines.get(i), pivot, new Angle(Math.toRadians(-angularVelo / (1 / time))));
                    lines.set(i, newline);
                }

                for (int i = 0; i < circles.size(); i++) {
                    Circle newCircle = Geometry.rotateAround(circles.get(i), pivot, new Angle(Math.toRadians(-angularVelo / (1 / time))));
                    circles.set(i, newCircle);
                }

            }
        }
    }

    public double getTheta(){
        return theta;
    }

    @Override
    public void setPressed() {
        this.isPressed = !isPressed;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public double getXPos() {
        return xPos;
    }

    @Override
    public double getYPos() {
        return yPos;
    }

    @Override
    public void doAction() {
        if(theta == 90) {
            Timer timer = new Timer();
            if (!isPressed) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        while (theta > 90) {
                            moveFlipper(0.0167);
                        }
                        isPressed = false;
                    }
                }, 2000);
            }
            isPressed = true;
        }
    }

    @Override
    public void move(double x, double y) {
        xPos = x;
        yPos = y;
        pivot = new Vect(xPos+1.75,yPos+0.25);
        lines.clear();
        circles.clear();
        createLines(x,y);
        createCircles(x,y);
    }

    public String toString() {
        return "RightFlipper "+name+" "+xPos+" "+yPos;
    }
}
