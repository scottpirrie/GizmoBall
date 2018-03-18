package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddTriangleGizmoListener implements MouseListener {

    private Model m;
    private JPanel panel;
    private int L;

    AddTriangleGizmoListener(Model m, JPanel panel, int L){
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
        int xPos = e.getX() / L;
        int yPos = e.getY() / L;
        int size = m.getGizmos().size();
        if (xPos >= 20 || yPos >= 20) {
            JOptionPane.showMessageDialog(panel,
                    "Out of bounds",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            boolean success = m.addGizmo("triangle", "T" + size, String.valueOf(xPos), String.valueOf(yPos));
            if (!success) {
                JOptionPane.showMessageDialog(panel,
                        "Location already taken",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
