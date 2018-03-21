package src.model;

import src.physics.Circle;
import src.physics.LineSegment;

import java.util.List;

public interface Flipper {

    String getType();
    String getName();
    int getRotation();
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void moveFlipper(double time);
    double getTheta();
    void setPressed();
    boolean isPressed();
    double getXPos();
    double getYPos();
    void doAction();
    void move(double xPos,double yPos);

}
