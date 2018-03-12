package controller;

import model.AbstractGizmo;
import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.*;

public class MoveGizmoListener implements MouseListener,MouseMotionListener{

    private Board board;
    private Model model;
    private int startingX;
    private int startingY;
    private int moveBackX;
    private int moveBackY;
    private int timesClicked;

    public MoveGizmoListener(Board board, Model model){
        timesClicked = 0;
        this.board = board;
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        timesClicked++;

        if(timesClicked==1){
            startingX=e.getX()/board.getL();
            startingY=e.getY()/board.getL();
            moveBackX=e.getX()/board.getL();
            moveBackY=e.getY()/board.getL();
        }else if(timesClicked==2){
            if(model.findGizmo(e.getX() / board.getL(), e.getY() / board.getL()) != null) {
                AbstractGizmo gizmo = model.findGizmo(e.getX() / board.getL(), e.getY() / board.getL());
                model.remove(e.getX() / board.getL(), e.getY() / board.getL());
                model.addGizmo(gizmo.getType(), gizmo.getName(), String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                timesClicked = 0;
            }
        }

    }


    @Override
    public void mouseReleased(MouseEvent e) {

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
            if (model.findGizmo(startingX, startingY) != null) {
                AbstractGizmo gizmo = model.findGizmo(startingX, startingY);
                model.remove(startingX, startingY);
                boolean success = model.addGizmo(gizmo.getType(), gizmo.getName(), String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));

                if (!success) {
                    JOptionPane.showMessageDialog(board,
                            "Other gizmo in this location",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                    model.addGizmo(gizmo.getType(), gizmo.getName(), String.valueOf(moveBackX), String.valueOf(moveBackY));
                    timesClicked = 0;
                }
                startingX = e.getX() / board.getL();
                startingY = e.getY() / board.getL();
            }
        }
    }

    public void moveGizmo(){

    }

    public void moveFlipper(){

    }

}
