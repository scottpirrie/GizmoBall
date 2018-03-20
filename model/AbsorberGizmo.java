package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class AbsorberGizmo{

    private final String type;
    private final String name;
    private double xPos1;
    private double yPos1;
    private double xPos2;
    private double yPos2;
    private Ball ball;
    private final List<LineSegment> lines;
    private final List<Circle> circles;

    AbsorberGizmo(String type, String name, double xPos1, double yPos1, double xPos2, double yPos2){
        this.type = type;
        this.name = name;
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        this.ball = null;
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        createLines(this.xPos1,this.xPos2,this.yPos1,this.yPos2);
        createCircles(this.xPos1,this.xPos2,this.yPos1,this.yPos2);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public double getxPos() {
        return xPos1;
    }

    public double getyPos() {
        return yPos1;
    }

    public double getxPos2() {
        return xPos2;
    }

    public double getyPos2() {
        return yPos2;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    private void createLines(double xPos1,double xPos2,double yPos1,double yPos2) {
        lines.clear();
        LineSegment l1 = new LineSegment(xPos1,yPos1,xPos2,yPos1); // TOP LINE
        LineSegment l2 = new LineSegment(xPos1,yPos2,xPos2,yPos2); // BOTTOM LINE
        LineSegment l3 = new LineSegment(xPos1,yPos1,xPos1,yPos2); // LEFT LINE
        LineSegment l4 = new LineSegment(xPos2,yPos1,xPos2,yPos2); // RIGHT LINE

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
    }

    private void createCircles(double xPos1,double xPos2,double yPos1,double yPos2) {
        circles.clear();
        physics.Circle c1 = new physics.Circle(xPos1,yPos1,0);
        physics.Circle c2 = new physics.Circle(xPos2,yPos2,0);
        physics.Circle c3 = new physics.Circle(xPos1,yPos2,0);
        physics.Circle c4 = new physics.Circle(xPos2,yPos1,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
    }

    public List<LineSegment> getLines() {
        return lines;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    void doAction() {
        if((xPos1 == 0 && yPos1==0) || (xPos2 == 0 && yPos2 == 0)){
            ball.setExactX(xPos2-0.25);
            ball.setExactY(yPos2+0.25);
            ball.setVelo(new Vect(0, 50));
        }else {
            ball.setExactX(xPos2-0.25);
            ball.setExactY(yPos1-0.25);
            ball.setVelo(new Vect(0, -50));
        }
        ball.start();
        setBall(null);
    }

    public void move(double xPos1,double xPos2,double yPos1,double yPos2){
        this.xPos1=xPos1;
        this.xPos2=xPos2;
        this.yPos1=yPos1;
        this.yPos2 = yPos2;
        createCircles(this.xPos1,this.xPos2,this.yPos1,this.yPos2);
        createLines(this.xPos1,this.xPos2,this.yPos1,this.yPos2);
    }

    public String toString(){
        return "Absorber "+name+" "+xPos1+" "+yPos1+" "+xPos2+" "+yPos2;
    }

}
