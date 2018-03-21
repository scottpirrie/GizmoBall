package src.view;

import src.controller.MainMenuListener;
import src.model.Model;

import javax.swing.*;
import java.awt.*;

public class GizmoView extends JFrame {

    private Model model;

    public GizmoView(Model model){
        this.model = model;
        init();
    }

    private void init(){
        createLayout();
        setTitle("Gizmoball - Menu");
        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createLayout(){

        ClassLoader cl = this.getClass().getClassLoader();
        ImageIcon runIcon = new ImageIcon(cl.getResource("run.png"));
        ImageIcon buildIcon = new ImageIcon(cl.getResource("build.png"));
        ImageIcon quitIcon = new ImageIcon(cl.getResource("quit.png"));
        ImageIcon logoIcon = new ImageIcon(cl.getResource("logoGizmoball.png"));

        JPanel logo = new JPanel();
        JLabel label = new JLabel(logoIcon);
        logo.add(label);
        this.add(logo, BorderLayout.PAGE_START);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));

        JButton button = new JButton("Run", runIcon);
        button.addActionListener(new MainMenuListener(this,model));
        panel.add(button);

        button = new JButton("Build", buildIcon);
        button.addActionListener(new MainMenuListener(this,model));
        panel.add(button);

        button = new JButton("Quit", quitIcon);
        button.addActionListener(new MainMenuListener(this,model));

        panel.add(button);

        this.add(panel);
    }

}
