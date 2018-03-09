package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RemoveGizmo implements MouseListener{

    private Board panel;
    private Model model;

    public RemoveGizmo(Board panel, Model model){
        this.panel=panel;
        this.model=model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double xPos=e.getX();
        double yPos=e.getY();
        boolean success = model.removeGizmo(xPos,yPos,panel.getL());
        if(success==false){
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
