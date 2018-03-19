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

        if(timesClicked==1){
            String gizmo = model.findGizmo((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
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

        }else if(timesClicked==2){

            String gizmo = model.findGizmo((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
            //model.remove((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
            String[] attributes = gizmo.split(" ");

            switch (attributes[0]){
                case "square":
                    model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "triangle":
                    model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "circle":
                    model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "rightflipper":
                    model.MoveFlipper(attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "leftflipper":
                    model.MoveFlipper(attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "absorber":
                    double height = Double.parseDouble(attributes[4]);
                    double width = Double.parseDouble(attributes[5]);

                    double newXPos2=  (Double.parseDouble(attributes[2])+width);
                    double newYPos2=  (Double.parseDouble(attributes[3])+height);
                    model.moveAbsorber(attributes[1],attributes[2], attributes[3],String.valueOf(newXPos2),String.valueOf(newYPos2));

                    break;
                case "ball":
                    model.moveBall(attributes[1],String.valueOf((double)e.getX() / board.getL()), String.valueOf((double)e.getY() / board.getL()));
                    model.setNewBallsTakenPoints();
                    break;
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

            System.out.println(startingX+" "+startingY);
            String gizmo = model.findGizmo(startingX,startingY);
            System.out.println(gizmo);
            //model.remove(startingX,startingY);
            String[] attributes = gizmo.split(" ");
            boolean success=false;
            if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {
                if((e.getX() / board.getL())<20 && (e.getY() / board.getL())<20) {

               success= model.moveGizmo(attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                }else{
                    outOfBounds=true;
                }
            }else if((attributes[0].equals("rightflipper")||attributes[0].equals("leftflipper"))){
                if((e.getX() / board.getL())<19 && (e.getY() / board.getL())<19){
                    System.out.println("Move to: "+e.getX()/board.getL()+" "+e.getY()/board.getL());
                    success= model.MoveFlipper(attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                }else{
                    outOfBounds=true;
                }
            }else if(attributes[0].equals("ball")){
                if((e.getX() / board.getL())<20 && (e.getY() / board.getL())<20) {

                    success = model.moveBall(attributes[1], String.valueOf((double) e.getX() / board.getL()), String.valueOf((double) e.getY() / board.getL()));
                }else{
                    outOfBounds=true;
                }
            }else if(attributes[0].equals("absorber")){
                double height = Double.parseDouble(attributes[4]);
                double width = Double.parseDouble(attributes[5]);

                    int newXPos2= (int) ((e.getX() / board.getL())+width);
                    int newYPos2= (int) ((e.getY() / board.getL())+height);

                if(newXPos2<=20 && newYPos2<=20){
                    success= model.moveAbsorber(attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),String.valueOf(newXPos2),String.valueOf(newYPos2));
                }else{
                    outOfBounds=true;
                }
            }

            if(!success) {
                if(outOfBounds){
                    JOptionPane.showMessageDialog(board,
                            "Attempting to place gizmo out of bounds",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                    outOfBounds=false;
                }else {
                    JOptionPane.showMessageDialog(board,
                            "Other gizmo in this location(moving)",
                            "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {
                    model.moveGizmo( attributes[1], String.valueOf(moveBackX), String.valueOf(moveBackY));
                }else if(attributes[0].equals("leftflipper")){
                    model.MoveFlipper(attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("rightflipper")){
                    model.MoveFlipper(attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("ball")){
                    model.moveBall(attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("absorber")){
                    double height = Double.parseDouble(attributes[4]);
                    double width = Double.parseDouble(attributes[5]);
                    int tempMoveBackX=(int)moveBackX;
                    int tempMoveBackY=(int)moveBackY;
                    double newXPos2=tempMoveBackX+width;
                    double newYPos2=tempMoveBackY+height;
                    model.moveAbsorber(attributes[1],String.valueOf(tempMoveBackX), String.valueOf(tempMoveBackY),String.valueOf(newXPos2),String.valueOf(newYPos2));

                }
               timesClicked=0;
           }

               startingX = (double) e.getX() / board.getL();
               startingY = (double) e.getY() / board.getL();

        }

    }

}
