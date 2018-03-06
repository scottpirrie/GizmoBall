package model;

public class GizmoFactory {

    private int L;

    public GizmoFactory(int L){
        this.L = L;
    }

    public AbstractGizmo createGizmo(String type, String name, String xPos, String yPos) {
        int x = Integer.parseInt(xPos);
        int y = Integer.parseInt(yPos);

        type = type.toLowerCase();

        switch (type) {
            case "square":
                return new SquareGizmo(type, name, x, y, L);
            case "circle":
                return new CircleGizmo(type, name, x + L / 2, y + L / 2, L / 2);
            case "triangle":
                return new TriangleGizmo(type, name, x, y, L);
        }

        return null;
    }

    //I don't believe that this gizmo actually needs to know L..
    //Furthermore i'm not sure whether or not I want this separate method..
    public AbstractGizmo createAbsorber(String type, String name, String xPos1, String yPos1, String xPos2, String yPos2) {
        int x1 = Integer.parseInt(xPos1);
        int y1 = Integer.parseInt(yPos1);
        int x2 = Integer.parseInt(xPos2);
        int y2 = Integer.parseInt(yPos2);
        type = type.toLowerCase();
        return new AbsorberGizmo(type,name,x1,y1,x2,y2);
    }

    //Does this make sense here?
    public Flipper createFlipper(String type,String name, String xPos, String yPos){
        int x = Integer.parseInt(xPos);
        int y = Integer.parseInt(yPos);

        type = type.toLowerCase();

        switch (type){
            case "leftflipper":
                return new LeftFlipper(type, name,x,y,L);
            case "rightflipper":
                return new RightFlipper(type, name,x,y,L);
        }

        return null;
    }

}
