package controller;

import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddTriggerListener implements MouseListener {

    private Board board;
    private Model model;
    private int sourceX;
    private int sourceY;
    private int timesClicked;

    AddTriggerListener(Board board, Model model){
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
            sourceX = e.getX()/board.getL();
            sourceY = e.getY()/board.getL();
        }else if(timesClicked==2){
            int targetX = e.getX()/board.getL();
            int targetY = e.getY()/board.getL();

            boolean success = model.addTrigger(model.findGizmoName(sourceX,sourceY),model.findGizmoName(targetX,targetY));

            if (!success) {
                JOptionPane.showMessageDialog(board,
                        "Trigger could not be added.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                timesClicked = 0;
            }else{
                JOptionPane.showMessageDialog(board, "Trigger Added");
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
}
