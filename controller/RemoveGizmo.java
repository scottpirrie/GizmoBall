package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RemoveGizmo implements MouseListener{

    private JPanel panel;
    private Model model;
    private int L;

    public RemoveGizmo(JPanel panel, Model model, int L){
        this.panel=panel;
        this.model=model;
        this.L = L;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xPos=e.getX()/L;
        int yPos=e.getY()/L;
        boolean success = model.removeGizmo(xPos,yPos);
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
