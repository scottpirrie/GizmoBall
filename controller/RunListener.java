package controller;

import model.Model;
import view.*;

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
        timer = new Timer(50, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            board.getModel().moveBall(0.05);
        } else {
            switch (e.getActionCommand()) {
                case "Start":
                    timer.start();
                    break;
                case "Tick":
                    board.getModel().moveBall(0.05);
                    break;
                case "Stop":
                    timer.stop();
                    break;
                case "Build Mode":
                    frame.dispose();
                    board.setBuildingMode(true);
                    Gui bGUI = new BuildGui(board);
                    bGUI.createAndShowGUI();
                    timer.stop();
                    break;
                case "Quit":
                    frame.dispose();
                    System.exit(0);
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
