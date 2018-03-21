package controller;

import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.*;

public class MoveGizmoListener implements MouseListener,MouseMotionListener{

    private Board board;
    private Model model;
    private double startingX;
    private double startingY;
    private double moveBackX;
    private double moveBackY;
    private int timesClicked;
    private boolean outOfBounds;

    MoveGizmoListener(Board board, Model model){
        timesClicked = 0;
        this.board = board;
        this.model = model;
        outOfBounds=false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        timesClicked++;
        String gizmo="";
        boolean success = false;
        boolean outOfBounds = false;
        gizmo = model.findGizmo((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
        if(timesClicked==1){
            String [] attributes=gizmo.split(" ");
            if(attributes[0].equals("ball")){
                model.cleanUpWhenBallMoves();
            }
            if(gizmo.equals("")){
                JOptionPane.showMessageDialog(board,
                        "No gizmo in this location",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                timesClicked=0;
            }else {
                if(attributes[0].equals("absorber")){
                    startingX=Double.parseDouble(attributes[2]);
                    startingY=Double.parseDouble(attributes[3]);
                }else {
                    startingX =  (double)e.getX() / board.getL();
                    startingY =  (double)e.getY() / board.getL();
                }
                try {
                    moveBackX = Double.parseDouble(attributes[2]);
                    moveBackY = Double.parseDouble(attributes[3]);
                }catch (NumberFormatException event){
                    moveBackX = Integer.parseInt(attributes[2]);
                    moveBackY = Integer.parseInt(attributes[3]);
                }
            }

        }else if(timesClicked==2) {

            gizmo = model.findGizmo(startingX, startingY);

            String[] attributes = gizmo.split(" ");

                switch (attributes[0]) {
                    case "square":
                        if((e.getX() / board.getL())<20 && (e.getY() / board.getL())<20) {
                            success = model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                        }else{
                            outOfBounds=true;
                        }
                        break;
                    case "triangle":
                        if((e.getX() / board.getL())<20 && (e.getY() / board.getL())<20) {
                            success = model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                        }else{
                            outOfBounds=true;
                        }
                        break;
                    case "circle":
                        if((e.getX() / board.getL())<20 && (e.getY() / board.getL())<20) {
                            success = model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                        }else{
                            outOfBounds=true;
                        }
                        break;
                    case "rightflipper":
                        if((e.getX() / board.getL())<19 && (e.getY() / board.getL())<19) {
                            success = model.moveFlipper(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                        }else {
                            outOfBounds=true;
                        }
                        break;
                    case "leftflipper":
                        if((e.getX() / board.getL())<19 && (e.getY() / board.getL())<19) {
                            success = model.moveFlipper(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                        }else {
                            outOfBounds=true;
                        }
                        break;
                    case "absorber":
                        double height = Double.parseDouble(attributes[4]);
                        double width = Double.parseDouble(attributes[5]);
                        double xPos = e.getX() / board.getL();
                        double yPos = e.getY() / board.getL();
                        double newXPos2 = xPos+ width;
                        double newYPos2 = yPos + height;

                        if(newXPos2<=20 && newYPos2<=20) {
                            success = model.moveAbsorber(attributes[1], String.valueOf(xPos), String.valueOf(yPos), String.valueOf(newXPos2), String.valueOf(newYPos2));
                        }else{
                            outOfBounds=true;
                        }

                        break;
                    case "ball":
                        if(e.getX()<20*board.getL()-board.getL()/4 && e.getY()<20*board.getL()-board.getL()/4 && e.getX()>board.getL()/4 && e.getY()>board.getL()/4) {
                            success = model.moveBall(attributes[1], String.valueOf((double) e.getX() / board.getL()), String.valueOf((double) e.getY() / board.getL()));
                            model.setNewBallsTakenPoints();
                        }else{
                            outOfBounds=true;
                        }

                        break;
                }

                timesClicked = 0;
                if(!success){
                    if(outOfBounds){
                        JOptionPane.showMessageDialog(board,
                                "Try to place gizmo out of bounds",
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }else {
                        JOptionPane.showMessageDialog(board,
                                "Other gizmo in this location",
                                "Inane error",
                                JOptionPane.ERROR_MESSAGE);
                    }
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

    }

}
