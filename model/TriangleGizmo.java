package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class TriangleGizmo implements AbstractGizmo{


    private String type;
    private String name;
    private int xPos;
    private int yPos;
    private int width;
    private int rotation;
    private List<LineSegment> lines;
    private List<Circle> circles;


    public TriangleGizmo(String type, String name,int xPos,int yPos,int width){
        this.type = type;
        this.name = name;
        this.xPos=xPos;
        this.yPos=yPos;
        this.width=width;
        this.rotation = 0;
        lines = new ArrayList<>();
        circles = new ArrayList<>();
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
    public int getxPos() {
        return xPos;
    }

    @Override
    public int getyPos() {
        return yPos;
    }

    @Override
    public void createLines() {
        LineSegment l1 = new LineSegment(xPos,yPos,xPos+width,yPos);
        LineSegment l2 = new LineSegment(xPos+width,yPos,xPos,yPos+width);
        LineSegment l3 = new LineSegment(xPos,yPos+width,xPos+width,yPos+width);

        lines.add(l1);
        lines.add(l2);
        lines.add(l3);
    }

    @Override
    public void createCircles() {
        Circle c1 = new Circle(xPos,yPos,0);
        Circle c2 = new Circle(xPos+width,yPos,0);
        Circle c3 = new Circle(xPos,yPos+width,0);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
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
        //TODO Probably need to find a better way than the 50-line chunk! But its always there if needed!
    }

    @Override
    public String toString() {
        return "Triangle "+name+" "+xPos+" "+yPos;
    }

}
