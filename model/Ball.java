package model;

import physics.Circle;
import physics.Vect;
import java.awt.*;
import java.time.Year;

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

    Vect getVelo() {
        return velocity;
    }

    void setVelo(Vect v) {
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

    void setExactX(double x) {
        xpos = x;
    }

    void setExactY(double y) {
        ypos = y;
    }

    void stop() {
        stopped = true;
    }

    void start() {
        stopped = false;
    }

    boolean stopped() {
        return stopped;
    }

    public Color getColour() {
        return colour;
    }

    public String toString(){
        return "Ball "+"B "+Math.round(xpos)+" "+Math.round(ypos)+" "+Math.round(velocity.x())+" "+Math.round(velocity.y());
    }
    void movePoints(double xPos,double ypos){
        this.xpos=xPos;
        this.ypos= ypos;
    }

}
