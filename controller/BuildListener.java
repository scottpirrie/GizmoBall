package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildListener implements ActionListener {

    private JFrame frame;
    private Board board;
    //pass through the model
    public BuildListener(JFrame view, Board board){
        frame = view;
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run Mode":
                frame.dispose();
                Gui rGUI = new RunGui(board);
                rGUI.createAndShowGUI();
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
