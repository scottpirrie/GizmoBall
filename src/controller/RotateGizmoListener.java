package src.controller;

import src.model.Model;
import src.view.Board;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RotateGizmoListener implements MouseListener{


    private Board panel;
    private Model model;

    RotateGizmoListener(Board panel, Model model){
        this.panel=panel;
        this.model=model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double L = panel.getL();
        double xPos = e.getX()/L;
        double yPos = e.getY()/L;

        boolean success = model.rotate(xPos,yPos);
        if(!success){
            JOptionPane.showMessageDialog(panel,
                    "No gizmo in this location",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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