package model;

import physics.Circle;
import physics.LineSegment;

import java.util.List;

public class RightFlipper implements Flipper{

    public RightFlipper(){

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void createLines() {

    }

    @Override
    public void createCircles() {

    }

    @Override
    public List<LineSegment> getLines() {
        return null;
    }

    @Override
    public List<Circle> getCircles() {
        return null;
    }

    @Override
    public void rotate() {

    }

    @Override
    public void moveFlipper(double time) {

    }

    @Override
    public void setPressed(boolean isPressed) {

    }

    @Override
    public boolean isPressed() {
        return false;
    }

    @Override
    public int getXPivot() {
        return 0;
    }

    @Override
    public int getYPivot() {
        return 0;
    }

    @Override
    public double getXArc() {
        return 0;
    }

    @Override
    public double getYArc() {
        return 0;
    }
}
