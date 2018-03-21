package src.controller;

import src.view.*;

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
                String YesNo[] = {"Yes","No"};
                int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to quit?","Gizmoball - BuildMode",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,YesNo,YesNo[1]);
                if(choice==JOptionPane.YES_OPTION){
                    frame.dispose();
                    System.exit(0);
                    break;
                }
                break;
            case "Save":
                SaveView save = new SaveView(board.getModel());
                break;
            case "Load":
                LoadView load = new LoadView(board.getModel());
                break;
        }
    }

    private void removeListeners() {
        MouseListener[] listeners = board.getMouseListeners();
        for (MouseListener listener : listeners) {
            board.removeMouseListener(listener);
        }
    }
}
