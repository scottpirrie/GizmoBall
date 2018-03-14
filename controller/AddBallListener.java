package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddBallListener implements MouseListener{

    private Model m;
    private JPanel panel;
    private double L;

    public AddBallListener(Model m, JPanel panel, int L){
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
        double x = e.getX() / L;
        double y = e.getY() / L;
        double xV = 5.0;
        double yV = 5.0;
        int size = m.getBalls().size();
        if (x >= 20 || y >= 20) {
            JOptionPane.showMessageDialog(panel,
                    "Out of bounds",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            boolean success = m.addBall("ball", "B" + size, String.valueOf(x), String.valueOf(y), String.valueOf(xV), String.valueOf(yV));

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
