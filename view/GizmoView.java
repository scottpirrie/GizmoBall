package view;

import controller.MainMenuAL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The GizmoView will be the main menu for the Gizmoball Application
 */

public class GizmoView extends JFrame {

    private JButton button;
    private GridLayout layout;
    private JPanel panel;
    private JPanel logo;
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
        ImageIcon runIcon = new ImageIcon("run.png");
        ImageIcon buildIcon = new ImageIcon("build.png");
        ImageIcon quitIcon = new ImageIcon("quit.png");
        ImageIcon logoIcon = new ImageIcon("logoGizmoball.png");

        logo = new JPanel();
        JLabel label = new JLabel(logoIcon);
        logo.add(label);
        this.add(logo, BorderLayout.PAGE_START);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        button = new JButton("Run", runIcon);
        button.addActionListener(menuAL);
        panel.add(button);

        button = new JButton("Build", buildIcon);
        button.addActionListener(menuAL);
        panel.add(button);

        button = new JButton("Quit", quitIcon);
        button.addActionListener(menuAL);

        panel.add(button);

        this.add(panel);
    }

}
