package view;

import controller.MainMenuAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The GizmoView will be the main menu for the Gizmoball Application
 */

public class GizmoView extends JFrame {

    private Gui runGui;
    private Gui buildGui;
    private Board board;
    private JButton button;
    private GridLayout layout;
    private JPanel panel;
    private ActionListener menuAL;

    //private Model model;

    public GizmoView(){
        init();
    }

    private void init(){
        //need to pass through runGUI, buildGUI and Board
        menuAL = new MainMenuAL(this);
        createLayout();
        setTitle("Gizmoball - Menu");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(){
        layout = new GridLayout(0,1);
        panel = new JPanel();
        panel.setLayout(layout);

        button = new JButton("Run Gizmoball");
        button.addActionListener(menuAL);
        panel.add(button);

        button = new JButton("Build Gizmoball");
        button.addActionListener(menuAL);
        panel.add(button);

        button = new JButton("Quit");
        button.addActionListener(menuAL);
        panel.add(button);

        this.add(panel);
    }

}
