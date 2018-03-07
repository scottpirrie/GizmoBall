package controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import model.Model;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RemoveGizmo implements MouseListener{

    private JPanel panel;
    private Model model;

    public RemoveGizmo(JPanel panel, Model model){
        this.panel=panel;
        this.model=model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xPos=e.getX()/25;
        int yPos=e.getY()/25;
        boolean success = model.removeGizmo(xPos,yPos);
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
