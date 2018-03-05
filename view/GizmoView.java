package view;

import controller.MainMenuListener;

import javax.swing.*;
import java.awt.*;

/**
 * The GizmoView will be the main menu for the Gizmoball Application
 */

public class GizmoView extends JFrame {

    private GridLayout layout;
    //private Model model;

    public GizmoView(){
        init();
    }

    private void init(){
        //need to pass through runGUI, buildGUI and Board
        createLayout();
        setTitle("Gizmoball - Menu");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createLayout(){
        ImageIcon runIcon = new ImageIcon("run.png");
        ImageIcon buildIcon = new ImageIcon("build.png");
        ImageIcon quitIcon = new ImageIcon("quit.png");
        ImageIcon logoIcon = new ImageIcon("logoGizmoball.png");

        JPanel logo = new JPanel();
        JLabel label = new JLabel(logoIcon);
        logo.add(label);
        this.add(logo, BorderLayout.PAGE_START);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JButton button = new JButton("Run", runIcon);
        button.addActionListener(new MainMenuListener(this));
        panel.add(button);

        button = new JButton("Build", buildIcon);
        button.addActionListener(new MainMenuListener(this));
        panel.add(button);

        button = new JButton("Quit", quitIcon);
        button.addActionListener(new MainMenuListener(this));

        panel.add(button);

        this.add(panel);
    }

}
