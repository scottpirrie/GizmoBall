package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;

public interface Flipper {

    String getType();
    String getName();
    int getRotation();
    void createLines(double xPos,double yPos);
    void createCircles(double xPos,double yPos);
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void moveFlipper(double time);
    double getThetaCheck();
    void setPressed();
    boolean isPressed();
    double getXPivot();
    double getYPivot();
    void doAction();
    void move(double xPos,double yPos);

}
