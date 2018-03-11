package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddSquareGizmoListener implements MouseListener {

    private Model m;
    private JPanel panel;
    private int L;

    public AddSquareGizmoListener(Model m, JPanel panel, int L){
        this.panel=panel;
        this.m = m;
        this.L = L;
    }

    //TODO the line "int xPos = e.getX()/m.getL();" was previously "int xPos = e.getX()/25;" we should avoid using
    //TODO absolute values for L ! but its minor and easily fixed with m.getL();
    //TODO also need to somehow increment the name to something like S0 , S1 , S3 - also minor!

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int xPos = e.getX()/L;
        int yPos = e.getY()/L;
        int size = m.getGizmos().size();
        boolean success=m.addGizmo("square","S"+size,String.valueOf(xPos),String.valueOf(yPos));
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
