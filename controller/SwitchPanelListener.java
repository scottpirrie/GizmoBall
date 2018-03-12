package controller;

import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SwitchPanelListener implements ActionListener{

    private Model m;
    private int L;
    private String type;
    private Board bl;

    public SwitchPanelListener(Model m, int L, Board bl) {
        this.m = m;
        this.L = L;
        this.bl = bl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private void removeListeners() {
        MouseListener[] listeners = bl.getMouseListeners();
        for (int i = 0; i < listeners.length; i++) {
            bl.removeMouseListener(listeners[i]);
        }
    }

    //TODO Make into a switch statement
    @Override
    public void actionPerformed(ActionEvent e) {

        removeListeners();
        if (e.getActionCommand().equals("Square")) {
            bl.addMouseListener(new AddSquareGizmoListener(m,bl,L));
        } else if (e.getActionCommand().equals("Triangle")) {
            bl.addMouseListener(new AddTriangleGizmoListener(m,bl,L));
        } else if (e.getActionCommand().equals("Circle")) {
            bl.addMouseListener(new AddCircleGizmoListener(m,bl,L));
        }else if(e.getActionCommand().equals("Remove")){
            bl.addMouseListener(new RemoveGizmo(bl,m));
        } else if(e.getActionCommand().equals("Left Flipper")){
            bl.addMouseListener(new AddLeftFlipperListener(bl,m));
        }else if(e.getActionCommand().equals("Right Flipper")){
            bl.addMouseListener(new AddRightFlipperListener(bl,m));
        }else if(e.getActionCommand().equals("Ball")){
            bl.addMouseListener(new AddBallListener(m,bl, L));
        }else if(e.getActionCommand().equals("Absorber")){
            AddAbsorberGizmoListener listener = new AddAbsorberGizmoListener(bl,m);
            bl.addMouseListener(listener);
            bl.addMouseMotionListener(listener);
        }else if(e.getActionCommand().equals("Move")){
            MoveGizmoListener listener = new MoveGizmoListener(bl,m);
            bl.addMouseListener(listener);
            bl.addMouseMotionListener(listener);
        }else if(e.getActionCommand().equals("Add Trigger")){
            AddTriggerListener listener = new AddTriggerListener(bl,m);
            bl.addMouseListener(listener);
        }else if(e.getActionCommand().equals("Remove Trigger")){
            RemoveTriggerListener listener = new RemoveTriggerListener(bl,m);
            bl.addMouseListener(listener);
        }
    }
}
