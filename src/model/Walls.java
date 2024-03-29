package src.model;

import src.physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

class Walls {

    private final String name;
    private final int xpos1;
    private final int ypos1;
    private final int ypos2;
    private final int xpos2;

    Walls() {
        this.name = "OuterWalls";
        xpos1 = 0;
        ypos1 = 0;
        xpos2 = 20;
        ypos2 = 20;
    }

    List<LineSegment> getLineSegments() {
        List<LineSegment> ls = new ArrayList<>();
        LineSegment l1 = new LineSegment(xpos1, ypos1, xpos2, ypos1);
        LineSegment l2 = new LineSegment(xpos1, ypos1, xpos1, ypos2);
        LineSegment l3 = new LineSegment(xpos2, ypos1, xpos2, ypos2);
        LineSegment l4 = new LineSegment(xpos1, ypos2, xpos2, ypos2);
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);
        ls.add(l4);
        return ls;
    }

    public String getName(){
        return name;
    }

}
