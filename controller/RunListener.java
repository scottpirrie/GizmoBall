package controller;

import view.BuildGui;
import view.Gui;
import view.RunGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunListener implements ActionListener {

    private JFrame frame;

    public RunListener(JFrame view){
        frame = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Build Mode":
                frame.dispose();
                Gui bGUI = new BuildGui();
                bGUI.createAndShowGUI();
                break;
        }
    }
}
