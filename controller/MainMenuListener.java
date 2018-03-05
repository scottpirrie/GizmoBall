package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuListener implements ActionListener {

    private GizmoView view;
    private Board board;

    public MainMenuListener(GizmoView frame){
        view = frame;
        board = new Board(500,500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run":
                //close current view and open new view
                System.out.println("Closing Main Menu.......");
                view.dispose();
                System.out.println("Opening Run GUI.......");
                Gui rGUI = new RunGui(board);
                rGUI.createAndShowGUI();
                break;
            case "Build":
                //close current view and open new view
                System.out.println("Closing Main Menu.......");
                view.dispose();
                Gui bGUI = new BuildGui(board);
                bGUI.createAndShowGUI();
                System.out.println("Opening Build GUI.........");
                break;
            case "Quit":
                System.out.println("Shutting down Gizmoball");
                System.exit(0);
                break;
        }
    }
}
