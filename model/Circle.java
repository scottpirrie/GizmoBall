package model;

public class Circle {

    private int xPos;
    private int yPos;
    private int radious;
    private physics.Circle circle;


    public Circle(int xPos,int yPos,int radious){
        this.radious=radious;
        this.xPos=xPos;
        this.yPos=yPos;
        circle = new physics.Circle(xPos,yPos,radious);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getRadious() {
        return radious;
    }

    public void setRadious(int radious) {
        this.radious = radious;
    }

    public physics.Circle getCircle() {
        return circle;
    }

    public void setCircle(physics.Circle circle) {
        this.circle = circle;
    }
}
