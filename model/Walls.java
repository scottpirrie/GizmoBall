package model;

import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

class Walls {

    private String name;
    private int xpos1;
    private int ypos1;
    private int ypos2;
    private int xpos2;

    Walls(String name,int x1, int y1, int x2, int y2) {
        this.name = name;
        xpos1 = x1;
        ypos1 = y1;
        xpos2 = x2;
        ypos2 = y2;
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


}
