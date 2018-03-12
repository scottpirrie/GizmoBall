package model;

import physics.Circle;
import physics.LineSegment;

import java.awt.*;
import java.util.List;

public interface AbstractGizmo {

    String getType();
    String getName();
    int getxPos();
    int getyPos();
    Color getColor();
    void createLines();
    void createCircles();
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();
    void doAction();
    void trigger();

}
