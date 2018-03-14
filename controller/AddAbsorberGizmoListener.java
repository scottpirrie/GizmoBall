package controller;

import model.Model;
import view.Board;

import javax.swing.*;
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

        timesClicked++;

        if(timesClicked==1){
            Point p = new Point(e.getX()/25,e.getY()/25);
            board.addAbsorberPoints(p);
            board.repaint();

        }else if(timesClicked==2){
            Point startingPoint = findStartingPoint();
            Point endingPoint = findEndingPoint();
            int size = board.getModel().getGizmos().size();
            boolean success=false;
            if(endingPoint.x>=20 || endingPoint.y>=20){
                JOptionPane.showMessageDialog(board,
                        "Out of bounds",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }else {

                success = model.addAbsorber("absorber", "A" + size, String.valueOf(startingPoint.x), String.valueOf(startingPoint.y), String.valueOf(endingPoint.x + 1), String.valueOf(endingPoint.y + 1));


                if (success) {
                    model.addTrigger("A" + size, "A" + size);
                }

                if (!success) {
                    JOptionPane.showMessageDialog(board,
                            "A location you choose is already taken",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
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
            Point p = new Point(e.getX() / 25, e.getY() / 25);
            board.addAbsorberPoints(p);
            board.repaint();
        }
    }

    private Point findStartingPoint(){
        int minY=board.getAbsorberPoints().get(0).y;
        int minX=board.getAbsorberPoints().get(0).x;

        for(Point p: board.getAbsorberPoints()){
            if(p.x<=minX) {
                minX=p.x;
            }
            if(p.y<=minY) {
                minY=p.y;
            }
        }
        return new Point(minX,minY);
    }

    private Point findEndingPoint(){
        int maxY=board.getAbsorberPoints().get(0).y;
        int maxX=board.getAbsorberPoints().get(0).x;

        for(Point p: board.getAbsorberPoints()){
            if(p.x>=maxX) {
                maxX=p.x;
            }
            if(p.y>=maxY ) {
                maxY=p.y;
            }

        }
        return new Point(maxX,maxY);
    }
}
