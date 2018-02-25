package controller;

import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuAL implements ActionListener {

    private GizmoView view;
    private Board board;

    public MainMenuAL(GizmoView frame){
        view = frame;
        board = new Board(500,500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run Gizmoball":
                //close current view and open new view
                System.out.println("Closing Main Menu.......");
                view.dispose();
                System.out.println("Opening Run GUI.......");
                Gui rGUI = new RunGui(board);
                rGUI.createAndShowGUI();
                break;
            case "Build Gizmoball":
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
