package controller;

import view.BuildGui;
import view.GizmoView;
import view.Gui;
import view.RunGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuAL implements ActionListener {

    private GizmoView view;

    public MainMenuAL(GizmoView frame){
        view = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run Gizmoball":
                //close current view and open new view
                System.out.println("Closing Main Menu.......");
                view.dispose();
                System.out.println("Opening Run GUI.......");
                Gui rGUI = new RunGui();
                rGUI.createAndShowGUI();
                break;
            case "Build Gizmoball":
                //close current view and open new view
                System.out.println("Closing Main Menu.......");
                view.dispose();
                Gui bGUI = new BuildGui();
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
