package model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class GizmoFactory {

    private final List<Point.Double> takenPoints;

    GizmoFactory(){
        takenPoints = new ArrayList<>();
    }

    AbstractGizmo createGizmo(String type, String name, String xPos, String yPos) {
        double x=0.0;
        double y=0.0;
        try {
            x = Math.floor(Double.parseDouble(xPos));
            y = Math.floor(Double.parseDouble(yPos));
        }catch (NumberFormatException e){
            return null;
        }

        type = type.toLowerCase();
        Point.Double p = new Point2D.Double(x,y);

        if (!takenPoints.contains(p)) {
            takenPoints.add(p);
            switch (type) {
                case "square":
                    return new SquareGizmo(type, name, x, y);
                case "circle":
                    return new CircleGizmo(type, name, x, y);
                case "triangle":
                    return new TriangleGizmo(type, name, x, y);
            }
        }
        return null;
    }

    AbsorberGizmo createAbsorber(String type, String name, String xPos1, String yPos1, String xPos2, String yPos2) {
        boolean foundTakenPoint=false;

        double x1 = 0.0;
        double y1 = 0.0;
        double x2 = 0.0;
        double y2=0.0;
        try {
            x1 = Double.parseDouble(xPos1);
            y1 = Double.parseDouble(yPos1);
            x2 = Double.parseDouble(xPos2);
            y2 = Double.parseDouble(yPos2);
        }catch (NumberFormatException e){
            return null;
        }

        for(double i=y1; i<y2; i++){
            for(double j=x1; j<x2; j++){
                Point.Double p = new Point.Double(j,i);
                if(takenPoints.contains(p)){
                    foundTakenPoint=true;
                }
            }
        }

        if(!foundTakenPoint) {
            type = type.toLowerCase();
            for (double i = y1; i < y2; i++) {
                for (double j = x1; j < x2; j++) {
                    Point.Double p = new Point.Double(j,i);
                    takenPoints.add(p);
                }
            }
            return new AbsorberGizmo(type, name, x1, y1, x2, y2);
        }
        return null;
    }

    Flipper createFlipper(String type,String name, String xPos, String yPos){
        double x=0.0;
        double y=0.0;

        try {
            x = Double.parseDouble(xPos);
            y = Double.parseDouble(yPos);
        }catch (NumberFormatException e){
            return null;
        }

        x = Math.floor(x);
        y = Math.floor(y);

        type = type.toLowerCase();
        Point.Double p = new Point.Double(x, y);

        if(!takenPoints.contains(p)
                &&!takenPoints.contains(new Point.Double(p.x,p.y+1))
                &&!takenPoints.contains(new Point.Double(p.x+1,p.y+1))
                &&!takenPoints.contains(new Point.Double(p.x+1,p.y))) {

            takenPoints.add(p);
            takenPoints.add(new Point.Double(p.x+1,p.y));
            takenPoints.add(new Point.Double(p.x,p.y+1));
            takenPoints.add(new Point.Double(p.x+1,p.y+1));

            switch (type) {
                case "leftflipper":
                    return new LeftFlipper(type, name, x, y);
                case "rightflipper":
                    return new RightFlipper(type, name, x, y);
            }
        }
        return null;
    }

    void clearPoints(){
        takenPoints.clear();
    }

    void removeTakenPoint(double x,double y){
        Point.Double p = new Point.Double(x,y);
        takenPoints.remove(p);
    }

    void addTakenPoint(double x,double y){
        Point.Double p=new Point.Double(x,y);
        if(!takenPoints.contains(p)) {
            takenPoints.add(p);
        }
    }

    boolean isPointTaken(Point.Double p){
        return takenPoints.contains(p);
    }

    // new method to check if is an absorber point
    boolean isAbsorberPoint(AbsorberGizmo ab, Point.Double p){
        for(double i=ab.getyPos(); i<=ab.getyPos2(); i++){
            for(double j=ab.getxPos(); j<=ab.getxPos2(); j++){
                Point.Double temp = new Point.Double(j,i);
                if(temp.x==p.x && temp.y==p.y){
                    return true;
                }
            }
        }
        return false;
    }
}
