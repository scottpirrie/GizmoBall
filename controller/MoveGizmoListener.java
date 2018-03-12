package controller;

import model.AbstractGizmo;
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

            String gizmo = model.findGizmo(e.getX()/board.getL(),e.getY()/board.getL());
            String [] attributes=gizmo.split(" ");
            if(gizmo.equals("")){
                JOptionPane.showMessageDialog(board,
                        "No gizmo in this location",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                    timesClicked=0;
            }else {
                startingX = e.getX() / board.getL();
                startingY = e.getY() / board.getL();
                try {
                    moveBackX = Double.parseDouble(attributes[2]);
                    moveBackY = Double.parseDouble(attributes[3]);
                }catch (NumberFormatException event){
                    moveBackX = Integer.parseInt(attributes[2]);
                    moveBackY = Integer.parseInt(attributes[3]);
                }
            }

        }else if(timesClicked==2){
            String gizmo = model.findGizmo(e.getX()/board.getL(),e.getY()/board.getL());
            model.remove(e.getX()/board.getL(),e.getY()/board.getL());
            String[] attributes = gizmo.split(" ");

            if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {//done
                model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("rightflipper")||attributes[0].equals("leftflipper")){//done
                model.addFlipper(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("ball")){
                model.addBall(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),"25","24");
            }else if(attributes.equals("absorber")){
                model.addAbsorber(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),"23","23");
            }

            timesClicked=0;
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
            String gizmo = model.findGizmo(startingX,startingY);
            System.out.println(gizmo);
            System.out.println(model.remove(startingX,startingY));
            String[] attributes = gizmo.split(" ");
            boolean success=false;
            if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {//done
                 success=model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("rightflipper")||attributes[0].equals("leftflipper")){// done
               success= model.addFlipper(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("ball")){
                success=model.addBall(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),"25","24");
            }else if(attributes.equals("absorber")){

                double xDifference=(e.getX()/board.getL())-startingX;
                double yDifference = (e.getY()/board.getL())-startingY;

                double newXPos2 = (int) Integer.parseInt(attributes[4])+xDifference;
                double newYPos2 = (int) Integer.parseInt(attributes[5])+yDifference;
                model.addAbsorber(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),String.valueOf(newXPos2),String.valueOf(newYPos2));
            }



            if(!success) {
               JOptionPane.showMessageDialog(board,
                       "Other gizmo in this location",
                       "Inane error",
                       JOptionPane.ERROR_MESSAGE);
                if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {
                    model.addGizmo(attributes[0], attributes[1], String.valueOf(moveBackX), String.valueOf(moveBackY));
                }else if(attributes[0].equals("leftflipper")){
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("rightflipper")){
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(moveBackX-1),String.valueOf(moveBackY));
                }
               timesClicked=0;
           }
            startingX=e.getX()/board.getL();
            startingY=e.getY()/board.getL();
        }
    }

    public void moveGizmo(){

    }

    public void moveFlipper(){

    }

}
