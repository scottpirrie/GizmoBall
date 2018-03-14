package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class AbsorberGizmo{

    private String type;
    private String name;
    private int xPos1;
    private int yPos1;
    private int xPos2;
    private int yPos2;
    private int height;
    private Ball ball;
    private List<LineSegment> lines;
    private List<Circle> circles;

    AbsorberGizmo(String type, String name, int xPos1, int yPos1, int xPos2, int yPos2){
        this.type = type;
        this.name = name;
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        this.height = height;
        this.ball = null;
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        createLines();
        createCircles();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getxPos() {
        return xPos1;
    }

    public int getyPos() {
        return yPos1;
    }

    public int getxPos2() {
        return xPos2;
    }

    public int getyPos2() {
        return yPos2;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    private void createLines() {
        LineSegment l1 = new LineSegment(xPos1,yPos1,xPos2,yPos1); // TOP LINE
        LineSegment l2 = new LineSegment(xPos1,yPos2,xPos2,yPos2); // BOTTOM LINE
        LineSegment l3 = new LineSegment(xPos1,yPos1,xPos1,yPos2); // LEFT LINE
        LineSegment l4 = new LineSegment(xPos2,yPos1,xPos2,yPos2); // RIGHT LINE

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
    }

    private void createCircles() {
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
        ball.setExactX(xPos2-0.25);
        ball.setExactY(yPos1-0.25);
        ball.setVelo(new Vect(0,-170));
        ball.start();
        setBall(null);
    }

    public String toString(){
        return "Absorber "+name+" "+xPos1+" "+yPos1+" "+xPos2+" "+yPos2;
    }

}
