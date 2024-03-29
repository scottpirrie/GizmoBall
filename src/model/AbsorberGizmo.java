package src.model;

import src.physics.Circle;
import src.physics.LineSegment;
import src.physics.Vect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AbsorberGizmo{

    private final String type;
    private final String name;
    private double xPos1;
    private double yPos1;
    private double xPos2;
    private double yPos2;
    private final Queue<Ball> ballQueue;
    private final List<LineSegment> lines;
    private final List<Circle> circles;

    AbsorberGizmo(String type, String name, double xPos1, double yPos1, double xPos2, double yPos2){
        this.type = type;
        this.name = name;
        this.xPos1 = xPos1;
        this.yPos1 = yPos1;
        this.xPos2 = xPos2;
        this.yPos2 = yPos2;
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        this.ballQueue = new LinkedList<>();
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

    public Queue<Ball> getBallQueue() {
        return ballQueue;
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
        Circle c1 = new Circle(xPos1,yPos1,0);
        Circle c2 = new Circle(xPos2,yPos2,0);
        Circle c3 = new Circle(xPos1,yPos2,0);
        Circle c4 = new Circle(xPos2,yPos1,0);

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
        Ball ball = ballQueue.poll();
        if(ball != null) {
            if ((yPos1 == 0)) {
                ball.setExactX(xPos2 - 0.25);
                ball.setExactY(yPos2 + 0.25);
                ball.setVelo(new Vect(0, 50));
            } else {
                ball.setExactX(xPos2 - 0.25);
                ball.setExactY(yPos1 - 0.25);
                ball.setVelo(new Vect(0, -50));
            }
            ball.start();
        }
    }

    public void captureBall(Ball ball) {
        boolean XCheck = ball.getExactX() >= this.getxPos() && ball.getExactX() <= this.getxPos2();
        boolean YCheck = ball.getExactY() >= this.getyPos() - ball.getRadius()
                && ball.getExactY() <= this.getyPos2() + ball.getRadius();

        if (XCheck && YCheck) {
            ballQueue.add(ball);
            ball.stop();
            ball.setExactX(this.getxPos2() - ball.getRadius());
            ball.setExactY(this.getyPos2() - ball.getRadius());
        }
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
