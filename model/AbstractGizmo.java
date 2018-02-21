package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;

public interface AbstractGizmo {

    String getType();
    String getName();
    void createLines();
    void createCircles();
    List<LineSegment> getLines();
    List<Circle> getCircles();
    void rotate();

}
