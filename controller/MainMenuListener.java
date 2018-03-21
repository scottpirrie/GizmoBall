package controller;

import model.Model;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuListener implements ActionListener {

    private GizmoView view;
    private Board board;

    public MainMenuListener(GizmoView frame, Model model){
        view = frame;
        board = new Board(model,500,500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Run":
                view.dispose();
                Gui rGUI = new RunGui(board);
                rGUI.createAndShowGUI();
                break;
            case "Build":
                view.dispose();
                board.setBuildingMode(true);
                Gui bGUI = new BuildGui(board);
                bGUI.createAndShowGUI();
                break;
            case "Quit":
                String YesNo[] = {"Yes","No"};
                int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to quit?","Gizmoball - MainMenu",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,YesNo,YesNo[1]);
                if(choice==JOptionPane.YES_OPTION){
                    view.dispose();
                    System.exit(0);
                    break;
                }
                break;
        }
    }
}
