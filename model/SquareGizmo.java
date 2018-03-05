package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class SquareGizmo implements  AbstractGizmo {

    private String type;
    private String name;
    private int xPos;
    private int yPos;
    private int width;
    private List<LineSegment> lines;
    private List<Circle> circles;

    public SquareGizmo(String type,String name, int xPos, int yPos, int width){
        this.type = type;
        this.name = name;
        this.xPos=xPos;
        this.yPos=yPos;
        this.width=width;
        lines=new ArrayList<>();
        circles=new ArrayList<>();
        createLines();
        createCircles();

    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void createLines() {
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+width,yPos);
        LineSegment l2 = new LineSegment(xPos+width,yPos,xPos+width,yPos+width);
        LineSegment l3 = new LineSegment(xPos+width,yPos+width,xPos,yPos+width);
        LineSegment l4 = new LineSegment(xPos,yPos+width,xPos,yPos);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
        lines.add(l4);
    }

    @Override
    public void createCircles() {
        physics.Circle c1 = new physics.Circle(xPos,yPos,0);
        physics.Circle c2 = new physics.Circle(xPos+width,yPos,0);
        physics.Circle c3 = new physics.Circle(xPos+width,yPos+width,0);
        physics.Circle c4 = new physics.Circle(xPos,yPos+width,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
    }

    @Override
    public List<LineSegment> getLines() {
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        return circles;
    }

    @Override
    public void rotate() {

    }

    public String toString(){
        return "Square "+name+" "+xPos+" "+yPos;
    }

}
