package controller;

import view.Board;
import view.BuildGui;
import view.Gui;
import view.RunGui;

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
                Gui bGUI = new BuildGui(board);
                bGUI.createAndShowGUI();
                break;
        }
    }
}
