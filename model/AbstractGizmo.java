package model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.List;

public interface AbstractGizmo {

    String getType();
    String getName();
    int getRotation();
    double getxPos();
    double getyPos();
    Color getColor();
    void createLines(double xPos,double yPos);
    void createCircles(double xPos,double yPos);
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void doAction();
    void movePoints(double x,double y);

}
