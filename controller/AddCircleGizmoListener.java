package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddCircleGizmoListener implements MouseListener{

    private Model m;
    private JPanel panel;
    private int L;

    public AddCircleGizmoListener(Model m, JPanel panel, int L){
        this.panel=panel;
        this.m = m;
        this.L = L;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int xPos = e.getX()/L;
        int yPos=e.getY()/L;
        boolean success=m.addGizmo("circle","C",String.valueOf(xPos),String.valueOf(yPos));
        if(!success){
            JOptionPane.showMessageDialog(panel,
                    "Location already taken",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
