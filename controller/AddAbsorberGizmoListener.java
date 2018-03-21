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

    AddAbsorberGizmoListener(Board board,Model model){
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
            Point p = new Point(e.getX()/board.getL(),e.getY()/board.getL());
            board.addAbsorberPoints(p);


        }else if(timesClicked==2){
            int endX = e.getX()/board.getL();
            int endY = e.getY()/board.getL();

            int size = board.getModel().getAbsorbers().size();

            if(endX >= 20 || endY >= 20){
                JOptionPane.showMessageDialog(board,
                        "Out of bounds",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);

            }else {

                boolean success = model.addAbsorber("absorber", "A" + size, String.valueOf(board.getAbsorberPoints().get(0).x),
                        String.valueOf(board.getAbsorberPoints().get(0).y), String.valueOf(endX+1), String.valueOf(endY+1));

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
            board.repaint();
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
//        if(timesClicked==1) {
//            Point p = new Point(e.getX() / board.getL(), e.getY() / board.getL());
//            board.addAbsorberPoints(p);
//            board.repaint();
//        }
    }

//    private Point findStartingPoint(){
//        int minY=board.getAbsorberPoints().get(0).y;
//        int minX=board.getAbsorberPoints().get(0).x;
//
//        for(Point p: board.getAbsorberPoints()){
//            if(p.x<=minX) {
//                minX=p.x;
//            }
//            if(p.y<=minY) {
//                minY=p.y;
//            }
//        }
//        return new Point(minX,minY);
//    }

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
