package src.controller;

import src.model.Model;
import src.view.Board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class SwitchPanelListener implements ActionListener{

    private Model m;
    private int L;
    private Board bl;

    public SwitchPanelListener(Model m, int L, Board bl) {
        this.m = m;
        this.L = L;
        this.bl = bl;
    }

    //TODO Make into a switch statement
    @Override
    public void actionPerformed(ActionEvent e) {
        removeListeners();
        String command = e.getActionCommand();

        switch (command.toLowerCase()){
            case "square":
                bl.addMouseListener(new AddSquareGizmoListener(m,bl,L));
                break;
            case "triangle":
                bl.addMouseListener(new AddTriangleGizmoListener(m,bl,L));
                break;
            case "circle":
                bl.addMouseListener(new AddCircleGizmoListener(m,bl,L));
                break;
            case "remove":
                bl.addMouseListener(new RemoveGizmo(bl,m));
                break;
            case "left flipper":
                bl.addMouseListener(new AddLeftFlipperListener(bl,m));
                break;
            case "right flipper":
                bl.addMouseListener(new AddRightFlipperListener(bl,m));
                break;
            case "ball":
                bl.addMouseListener(new AddBallListener(m,bl, L));
                break;
            case "absorber":
                AddAbsorberGizmoListener absorberListener = new AddAbsorberGizmoListener(bl,m);
                bl.addMouseListener(absorberListener);
                bl.addMouseMotionListener(absorberListener);
                break;
            case "move":
                MoveGizmoListener moveListener = new MoveGizmoListener(bl,m);
                bl.addMouseListener(moveListener);
                bl.addMouseMotionListener(moveListener);
                break;
            case "add trigger":
                bl.addMouseListener(new AddTriggerListener(bl,m));
                break;
            case "remove trigger":
                bl.addMouseListener(new RemoveTriggerListener(bl,m));
                break;
            case "add keybind":
                bl.addMouseListener(new AddKeyBindListener(bl,m));
                break;
            case "remove keybind":
                bl.addMouseListener(new RemoveKeyBindListener(bl,m));
                break;
            case "rotate":
                bl.addMouseListener(new RotateGizmoListener(bl,m));
                break;
        }
    }

    private void removeListeners() {
        MouseListener[] listeners = bl.getMouseListeners();
        for (MouseListener listener : listeners) {
            bl.removeMouseListener(listener);
        }
    }
}
