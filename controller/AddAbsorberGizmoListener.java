package controller;

import model.Model;
import view.Board;

import java.awt.*;
import java.awt.event.*;

public class AddAbsorberGizmoListener implements MouseListener,MouseMotionListener{

    private int timesClicked=0;
    private Board board;
    private Model model;
    public AddAbsorberGizmoListener(Board board,Model model){
    this.model=model;
    this.board=board;
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("absorber to add");
        timesClicked++;
        if(timesClicked==1){
            Point p = new Point(e.getX()/25,e.getY()/25);
            board.addAbsorberPoints(p);
            board.repaint();
        }else if(timesClicked==2){
            System.out.println(board.getAbsorberPoints().toString());
            Point startingPoint = findStartingPoint();
            System.out.println("Starting point: "+startingPoint.toString());
            Point endingPoint = findEndingPoint();
            System.out.println("Ending point: "+endingPoint.toString());
            model.addAbsorber("absorber","A",String.valueOf(startingPoint.x),String.valueOf(startingPoint.y),String.valueOf(endingPoint.x+1),String.valueOf(endingPoint.y+1));
            board.clearAbsorberPoints();
            timesClicked=0;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(timesClicked==1) {
            Point startingPoint = board.getAbsorberPoints().get(0);
            Point p = new Point(e.getX() / 25, e.getY() / 25);
            board.addAbsorberPoints(p);
            board.repaint();
            System.out.println("Moving");
        }
    }

    public Point findStartingPoint(){
        int minY=board.getAbsorberPoints().get(0).y;
        int minX=board.getAbsorberPoints().get(0).x;
        Point toReturn=null;
        for(Point p: board.getAbsorberPoints()){
            if(p.x<=minX && p.y<=minY ) {
                minY=p.y;
               minX=p.x;
            }
        }
        toReturn=new Point(minX,minY);
        return toReturn;
    }

    public Point findEndingPoint(){
        int maxY=board.getAbsorberPoints().get(0).y;
        int maxX=board.getAbsorberPoints().get(0).x;
        Point toReturn=null;
        for(Point p: board.getAbsorberPoints()){
            if(p.x>=maxX && p.y>=maxY ) {
               maxY=p.y;
                maxX=p.x;
            }
        }
        toReturn=new Point(maxX,maxY);
        return toReturn;
    }
}
