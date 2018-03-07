package model;

import physics.Circle;
import physics.Vect;
import java.awt.*;

public class Ball {

    private String type;
    private String name;
    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private Color colour;
    private boolean stopped;

    public Ball(String type, String name, double x, double y, double xv, double yv, double radius) {
        this.type = type;
        this.name = name;
        this.xpos = x; // Centre coordinates
        this.ypos = y;
        this.colour = Color.BLUE;
        this.velocity = new Vect(xv, yv);
        this.radius = radius;
        this.stopped = false;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Vect getVelo() {
        return velocity;
    }

    public void setVelo(Vect v) {
        velocity = v;
    }

    public double getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(xpos, ypos, radius);
    }

    public double getExactX() {
        return xpos;
    }

    public double getExactY() {
        return ypos;
    }

    public void setExactX(double x) {
        xpos = x;
    }

    public void setExactY(double y) {
        ypos = y;
    }

    public void stop() {
        stopped = true;
    }

    public void start() {
        stopped = false;
    }

    public boolean stopped() {
        return stopped;
    }

    public Color getColour() {
        return colour;
    }

    public String toString(){
        return "Ball "+"B "+Math.round(xpos)+" "+Math.round(ypos)+" "+Math.round(velocity.x())+" "+Math.round(velocity.y());
    }

}
