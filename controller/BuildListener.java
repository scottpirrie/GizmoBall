package controller;

import view.Gui;
import view.RunGui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildListener implements ActionListener {

    private JFrame frame;
    //pass through the model
    public BuildListener(JFrame view){
        frame = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run Mode":
                frame.dispose();
                Gui rGUI = new RunGui();
                rGUI.createAndShowGUI();
                break;
        }
    }

}
