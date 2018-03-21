package src.model;

import src.physics.Circle;
import src.physics.LineSegment;

import java.awt.*;
import java.util.List;

public interface AbstractGizmo {

    String getType();
    String getName();
    int getRotation();
    double getxPos();
    double getyPos();
    Color getColor();
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void doAction();
    void move(double x,double y);

}
