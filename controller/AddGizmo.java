package controller;

import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddGizmo implements MouseListener{

    private Model m;
    private int L;
    private String type;
    private JPanel bl;

    public AddGizmo(Model m, int L, JPanel bl) {
        this.m = m;
        this.L = L;
        this.bl = bl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        removeListeners();
        if (e.getComponent().getName().equals("Square")) {
            bl.addMouseListener(new AddSquareGizmoListener(m,bl,L));
        } else if (e.getComponent().getName().equals("Triangle")) {
            bl.addMouseListener(new AddTriangleGizmoListener(m,bl,L));
        } else if (e.getComponent().getName().equals("Circle")) {
            bl.addMouseListener(new AddCircleGizmoListener(m,bl,L));
        }else if(e.getComponent().getName().equals("Remove")){
            bl.addMouseListener(new RemoveGizmo(bl,m));
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void removeListeners() {
        MouseListener[] listeners = bl.getMouseListeners();
        for (int i = 0; i < listeners.length; i++) {
            bl.removeMouseListener(listeners[i]);
        }
    }

}
