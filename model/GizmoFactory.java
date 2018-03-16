package model;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

class GizmoFactory {

    private List<Point.Double> takenPoints;

    GizmoFactory(){
        takenPoints = new ArrayList<>();
    }

    AbstractGizmo createGizmo(String type, String name, String xPos, String yPos) {
        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);

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

        int x1 = Integer.parseInt(xPos1);
        int y1 = Integer.parseInt(yPos1);
        int x2 = Integer.parseInt(xPos2);
        int y2 = Integer.parseInt(yPos2);

        for(int i=y1; i<y2; i++){
            for(int j=x1; j<x2; j++){
                Point.Double p = new Point.Double(j,i);
                if(takenPoints.contains(p)){
                    foundTakenPoint=true;
                }
            }
        }
        if(!foundTakenPoint) {
            type = type.toLowerCase();
            for (int i = y1; i < y2; i++) {
                for (int j = x1; j < x2; j++) {
                    Point.Double p = new Point.Double(j,i);
                    takenPoints.add(p);
                }
            }
            return new AbsorberGizmo(type, name, x1, y1, x2, y2);
        }
        return null;
    }

    Flipper createFlipper(String type,String name, String xPos, String yPos){
        double x = Double.parseDouble(xPos);
        double y = Double.parseDouble(yPos);

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

    void removeTakenPoint(int x,int y){
        Point.Double p = new Point.Double(x,y);
        takenPoints.remove(p);
    }

    boolean isPointTaken(Point.Double p){
        return takenPoints.contains(p);
    }
}
