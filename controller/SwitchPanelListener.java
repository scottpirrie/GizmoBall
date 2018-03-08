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
    private JPanel bl;

    public SwitchPanelListener(Model m, int L, JPanel bl) {
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

    public void removeListeners() {
        MouseListener[] listeners = bl.getMouseListeners();
        for (int i = 0; i < listeners.length; i++) {
            bl.removeMouseListener(listeners[i]);
        }
    }

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
        }else if(e.getActionCommand().equals("Left Flipper")){
            bl.addMouseListener(new AddLeftFlipperListener());
        }else if(e.getActionCommand().equals("Right Flipper")){
            bl.addMouseListener(new AddRightFlipperListener());
        }else if(e.getActionCommand().equals("Ball")){
            bl.addMouseListener(new AddBallListener());
        }else if(e.getActionCommand().equals("Absorber")){
            AddAbsorberGizmoListener listener = new AddAbsorberGizmoListener((Board) bl,m);
            bl.addMouseListener(listener);
            bl.addMouseMotionListener(listener);
        }
    }
}
