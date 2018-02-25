package view;

import javax.swing.*;
import java.awt.*;

public class RunGui implements Gui {

    private JFrame frame;
    private Board board;
    private Container cp;
    private Font gf;
    //private Model model;

    //still need to pass through the model and board
    public RunGui(){

    }

    public void createAndShowGUI() {
        frame = new JFrame("Gizmoball - Run Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.setSize(300,200);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 12);

        createMenuBar();
        createButtons();

        frame.setLocationRelativeTo(null);
        //frame.pack();
        frame.setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOption;
        JMenuItem menuItem;

        menuOption = new JMenu("Option");
        menuItem = new JMenuItem("Save");
        //add the action listener for Saving
        menuOption.add(menuItem);
        menuItem = new JMenuItem("Load");
        //add the action listener for Loading
        menuOption.add(menuItem);
        menuItem = new JMenuItem("Quit");
        //add the action listener for Quit
        menuOption.add(menuItem);
        menuBar.add(menuOption);


        menuOption = new JMenu("Switch Mode");
        menuItem = new JMenuItem("Build Mode");
        //add the action listener for switching mode
        menuOption.add(menuItem);
        menuBar.add(menuOption);

        frame.setJMenuBar(menuBar);

    }

    private void createButtons(){
        //still need the action listeners for the buttons.
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton button1 = new JButton("Start");
        button1.setFont(gf);
        button1.setMaximumSize(new Dimension(100, 100));
        buttons.add(button1);

        JButton button2 = new JButton("Stop");
        button2.setFont(gf);
        button2.setMaximumSize(new Dimension(100, 100));
        buttons.add(button2);

        JButton button4 = new JButton("Tick");
        button4.setFont(gf);
        button4.setMaximumSize(new Dimension(100, 100));
        buttons.add(button4);


        cp.add(buttons, BorderLayout.PAGE_END);
    }


}
