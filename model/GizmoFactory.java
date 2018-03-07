package model;


import java.awt.*;
import java.util.ArrayList;

class GizmoFactory {

    private ArrayList<Point> takenPoints;

    GizmoFactory(){
        takenPoints = new ArrayList<>();
    }

    AbstractGizmo createGizmo(String type, String name, String xPos, String yPos) {
        int x = Integer.parseInt(xPos);
        int y = Integer.parseInt(yPos);
        type = type.toLowerCase();
        Point p = new Point(x, y);

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
        int x1 = Integer.parseInt(xPos1);
        int y1 = Integer.parseInt(yPos1);
        int x2 = Integer.parseInt(xPos2);
        int y2 = Integer.parseInt(yPos2);
        type = type.toLowerCase();

        return new AbsorberGizmo(type,name,x1,y1,x2,y2);
    }

    Flipper createFlipper(String type,String name, String xPos, String yPos){
        int x = Integer.parseInt(xPos);
        int y = Integer.parseInt(yPos);
        type = type.toLowerCase();
        System.out.println(x + " " + y);
        switch (type){
            case "leftflipper":
                return new LeftFlipper(type, name,x,y);
            case "rightflipper":
                return new RightFlipper(type, name,x,y);
        }
        return null;
    }

    void clearPoints(){
        takenPoints.clear();
    }

    void removeTakenPoint(int x,int y){
        Point p = new Point(x,y);
        takenPoints.remove(p);
    }
}
