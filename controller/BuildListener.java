package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class BuildListener implements ActionListener {

    private JFrame frame;
    private Board board;

    public BuildListener(JFrame view, Board board){
        frame = view;
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run Mode":
                frame.dispose();
                board.setBuildingMode(false);
                removeListeners();
                Gui rGUI = new RunGui(board);
                rGUI.createAndShowGUI();
                break;
            case "Quit":
                frame.dispose();
                System.exit(0);
                break;
            case "Save":
                SaveView save = new SaveView(board.getModel());
                break;
            case "Load":
                LoadView load = new LoadView(board.getModel());
                break;
        }
    }
    public void removeListeners() {
        MouseListener[] listeners = board.getMouseListeners();
        for (int i = 0; i < listeners.length; i++) {
            board.removeMouseListener(listeners[i]);
        }
    }
}
