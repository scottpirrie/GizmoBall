package src.controller;

import src.model.Model;
import src.view.Board;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RemoveKeyBindListener implements MouseListener {

    private Board board;
    private Model model;
    private int key;

    public RemoveKeyBindListener(Board board, Model model){
        this.board = board;
        this.model = model;
        getKey();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double L = board.getL();
        double xPos = e.getX()/L;
        double yPos = e.getY()/L;
        boolean success = model.removeKeybind(key, model.findGizmoName(xPos, yPos));

        if (!success) {
            JOptionPane.showMessageDialog(board,
                    "No gizmo in this location or no keybind found",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(board, "KeyBind Removed");
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

    private void getKey(){
        String message = JOptionPane.showInputDialog(board,
                "Please press the key and then select the gizmo you wish to disconnect.",
                "Enter a single key here...");

        if(message != null && !message.isEmpty()) {
            if (message.length() > 1) {
                JOptionPane.showMessageDialog(board,
                        "Please only enter the key you wish to disconnect.",
                        "", JOptionPane.ERROR_MESSAGE);
            } else {
                key = message.toLowerCase().charAt(0);
            }
        }
    }
}
