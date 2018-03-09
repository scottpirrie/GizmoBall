package controller;

import model.Model;
import physics.Vect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        System.out.println("ball to add");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double x = e.getX()/L;
        double y = e.getY()/L;

        //for testing
        System.out.println("x " + x + " y " + y);

        double xV = 5.0;
        double yV = 5.0;


        boolean success = m.addBall("ball", "B", String.valueOf(x), String.valueOf(y), String.valueOf(xV), String.valueOf(yV));

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
