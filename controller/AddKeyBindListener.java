package controller;

import model.Model;
import view.Board;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddKeyBindListener implements MouseListener{

    private Board board;
    private Model model;
    private int key;

    public AddKeyBindListener(Board board, Model model){
        this.board = board;
        this.model = model;
        getKey();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double L = board.getL();
        double xPos = e.getX()/L;
        double yPos = e.getY()/L;
        boolean success = false;
        String name  = model.findName(xPos, yPos);

        if(!name.equals("")) {
            success = model.addKeyBind(key, name);
        }

        if (!success) {
            JOptionPane.showMessageDialog(board,
                    "Key is already bound or gizmo not selected.",
                    "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(board, "KeyBind Added");
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
                "Please press the key and then select the gizmo you wish to connect.",
                "Enter a single key here...");

        if(message != null && !message.isEmpty()) {
            if (message.length() > 1) {
                JOptionPane.showMessageDialog(board,
                        "Please only enter the key you wish to connect.",
                        "", JOptionPane.ERROR_MESSAGE);
            } else {
                key = message.toLowerCase().charAt(0);
            }
        }
    }
}
