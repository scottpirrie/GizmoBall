package controller;

import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddRightFlipperListener implements MouseListener{
    private Board board;
    private Model model;
    public AddRightFlipperListener(Board board, Model model){
        this.model=model;
        this.board=board;
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        int xPos=e.getX()/25;
        int yPos=e.getY()/25;
        boolean success = model.addFlipper("rightflipper","f",String.valueOf(xPos),String.valueOf(yPos));
        if(!success){
            JOptionPane.showMessageDialog(board,
                    "Location already taken",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("left lipper to add");
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
