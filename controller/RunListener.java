package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunListener implements ActionListener {

    private JFrame frame;
    private Board board;

    public RunListener(JFrame view, Board board){
        frame = view;
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Build Mode":
                frame.dispose();
                board.setBuildingMode(true);
                Gui bGUI = new BuildGui(board);
                bGUI.createAndShowGUI();
                break;
            case "Quit":
                frame.dispose();
                GizmoView view = new GizmoView();
                break;
            case "Save":
                SaveView save = new SaveView();
                break;
            case "Load":
                LoadView load = new LoadView();
                break;
        }
    }
}
