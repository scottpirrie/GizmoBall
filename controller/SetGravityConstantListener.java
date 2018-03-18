package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SetGravityConstantListener implements MouseListener {

    private Model m;

    public SetGravityConstantListener(Model m){
        this.m=m;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        JSlider slider = (JSlider) e.getComponent();
        double value = slider.getValue();
        m.setGravityConstant(value);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
