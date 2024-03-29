package src.controller;

import src.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunListener implements ActionListener {

    private Timer timer;
    private JFrame frame;
    private Board board;

    public RunListener(JFrame view, Board board){
        this.frame = view;
        this.board = board;
        timer = new Timer(17, this);
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            board.getModel().gameLoop(0.0167);
        } else {
            switch (e.getActionCommand()) {
                case "Start":
                    board.getModel().cleanUpWhenBallMoves();
                    timer.start();
                    break;
                case "Tick":
                    board.getModel().cleanUpWhenBallMoves();
                    board.getModel().gameLoop(0.0167);
                    board.getModel().setNewBallsTakenPoints();
                    break;
                case "Stop":
                    timer.stop();
                    board.getModel().setNewBallsTakenPoints();
                    break;
                case "Build Mode":
                    frame.dispose();
                    board.setBuildingMode(true);
                    Gui bGUI = new BuildGui(board);
                    bGUI.createAndShowGUI();
                    timer.stop();
                    board.getModel().setNewBallsTakenPoints();
                    break;
                case "Quit":
                    String YesNo[] = {"Yes","No"};
                    int choice = JOptionPane.showOptionDialog(null,"Are you sure you want to quit?","Gizmoball - RunMode",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,YesNo,YesNo[1]);
                    if(choice==JOptionPane.YES_OPTION)
                    {
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
    }
}
