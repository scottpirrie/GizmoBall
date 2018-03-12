package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;

public interface Flipper {

    String getType();
    String getName();
    void createLines();
    void createCircles();
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void moveFlipper(double time);
    double getThetaCheck();
    void setPressed(boolean isPressed);
    boolean isPressed();
    double getXPivot();
    double getYPivot();
    void doAction();

}
