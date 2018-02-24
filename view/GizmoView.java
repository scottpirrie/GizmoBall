package view;

import javax.swing.*;
import java.awt.*;

/**
 * The GizmoView will be the main menu for the Gizmoball Application
 */

public class GizmoView extends JFrame {

    private Gui runGui;
    private Gui buildGui;
    private JButton button;
    private GridLayout layout;
    private JPanel panel;

    //private Model model;

    public GizmoView(){
        init();
    }

    private void init(){
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
        //still need to add action listeners
        panel.add(button);

        button = new JButton("Build Gizmoball");
        //still need to add action listeners
        panel.add(button);

        button = new JButton("Quit");
        panel.add(button);

        this.add(panel);
    }

}
