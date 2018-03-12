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
            String gizmo = model.findGizmo((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
            String [] attributes=gizmo.split(" ");
            if(gizmo.equals("")){
                JOptionPane.showMessageDialog(board,
                        "No gizmo in this location",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                    timesClicked=0;
            }else {
                startingX = (double)e.getX() / board.getL();
                startingY = (double)e.getY() / board.getL();
                try {
                    moveBackX = Double.parseDouble(attributes[2]);
                    moveBackY = Double.parseDouble(attributes[3]);
                }catch (NumberFormatException event){
                    moveBackX = Integer.parseInt(attributes[2]);
                    moveBackY = Integer.parseInt(attributes[3]);
                }
                System.out.println("Move back positions: "+moveBackX+" "+moveBackY);
            }

        }else if(timesClicked==2){

            String gizmo = model.findGizmo((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
            model.remove((double)e.getX()/board.getL(),(double)e.getY()/board.getL());
            String[] attributes = gizmo.split(" ");

            switch (attributes[0]){
                case "square":
                    model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "triangle":
                    model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "circle":
                    model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "rightflipper":
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "leftflipper":
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
                    break;
                case "absorber":
                    int height = Integer.parseInt(attributes[4]);
                    int width = Integer.parseInt(attributes[5]);
                    int newXPos2=(e.getX() / board.getL())+width;
                    int newYPos2=(e.getY() / board.getL())+height;
                    model.addAbsorber(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),String.valueOf(newXPos2),String.valueOf(newYPos2));
                    break;
                case "ball":
                    model.addBall(attributes[0],attributes[1],String.valueOf((double)e.getX() / board.getL()), String.valueOf((double)e.getY() / board.getL()),"5.0","5.0");
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
            model.remove(startingX,startingY);
            String[] attributes = gizmo.split(" ");
            boolean success=false;
            if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {//done
                 success=model.addGizmo(attributes[0], attributes[1], String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("rightflipper")||attributes[0].equals("leftflipper")){// done
               success= model.addFlipper(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()));
            }else if(attributes[0].equals("ball")){
                success=model.addBall(attributes[0],attributes[1],String.valueOf((double)e.getX() / board.getL()),  String.valueOf((double)e.getY() / board.getL()),"5.0","5.0");
            }else if(attributes[0].equals("absorber")){
                int height = Integer.parseInt(attributes[4]);
                int width = Integer.parseInt(attributes[5]);
                 int newXPos2=(e.getX() / board.getL())+width;
                 int newYPos2=(e.getY() / board.getL())+height;
               success= model.addAbsorber(attributes[0],attributes[1],String.valueOf(e.getX() / board.getL()), String.valueOf(e.getY() / board.getL()),String.valueOf(newXPos2),String.valueOf(newYPos2));
            }



            if(!success) {
               JOptionPane.showMessageDialog(board,
                       "Other gizmo in this location(moving)",
                       "Inane error",
                       JOptionPane.ERROR_MESSAGE);
                if(attributes[0].equals("square")||attributes[0].equals("triangle")||attributes[0].equals("circle")) {
                    model.addGizmo(attributes[0], attributes[1], String.valueOf(moveBackX), String.valueOf(moveBackY));
                }else if(attributes[0].equals("leftflipper")){
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("rightflipper")){
                    model.addFlipper(attributes[0],attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY));
                }else if(attributes[0].equals("ball")){
                    model.addBall(attributes[0],attributes[1],String.valueOf(moveBackX),String.valueOf(moveBackY),"5.0","5.0");
                }else if(attributes[0].equals("absorber")){
                    int height = Integer.parseInt(attributes[4]);
                    int width = Integer.parseInt(attributes[5]);
                    int tempMoveBackX=(int)moveBackX;
                    int tempMoveBackY=(int)moveBackY;
                    int newXPos2=tempMoveBackX+width;
                    int newYPos2=tempMoveBackY+height;
                    model.addAbsorber(attributes[0],attributes[1],String.valueOf(tempMoveBackX), String.valueOf(tempMoveBackY),String.valueOf(newXPos2),String.valueOf(newYPos2));
                }
               timesClicked=0;
           }
            startingX=(double)e.getX()/board.getL();
            startingY=(double)e.getY()/board.getL();
        }
    }

    public void moveGizmo(){

    }

    public void moveFlipper(){

    }

}
