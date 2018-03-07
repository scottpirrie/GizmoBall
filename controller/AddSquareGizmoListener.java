package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddSquareGizmoListener implements MouseListener {

    private Model m;
    private JPanel panel;
    public AddSquareGizmoListener(Model m, JPanel panel){
        this.panel=panel;
        this.m = m;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int xPos = e.getX()/25;
        int yPos=e.getY()/25;
        boolean success=m.addGizmo("square","s",String.valueOf(xPos),String.valueOf(yPos));
        if(success==false){
            JOptionPane.showMessageDialog(panel,
                    "Location already taken",
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
