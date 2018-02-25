package view;

import controller.BuildListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BuildGui implements Gui {

    private JFrame frame;
    private Board board;
    private Container cp;
    private Font gf;
    private ActionListener listener;
    //private Model model;

    //still need to pass through the model and board
    public BuildGui(Board newBoard){
        board = newBoard;
    }

    public void createAndShowGUI(){
        frame = new JFrame("Gizmoball - Build Mode");

        listener = new BuildListener(frame, board);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 12);

        cp.add(board, BorderLayout.CENTER);

        createMenuBar();
        createGizmoButtons();
        createAdditionalButtons();

        frame.pack();
        frame.setLocationRelativeTo(null);
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
        menuItem = new JMenuItem("Run Mode");
        //add the action listener for switching mode
        menuItem.addActionListener(listener);
        menuOption.add(menuItem);
        menuBar.add(menuOption);

        frame.setJMenuBar(menuBar);
    }

    private void createGizmoButtons(){
        //still need the action listeners for the button
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,0));
        JPanel topButtons = new JPanel();
        topButtons.setLayout(new FlowLayout());

        JButton button = new JButton("Triangle");
        button = setUpButton(button);
        topButtons.add(button);

        button = new JButton("Left Flipper");
        button = setUpButton(button);
        topButtons.add(button);

        button = new JButton("Right Flipper");
        button = setUpButton(button);
        topButtons.add(button);

        JPanel lowButtons = new JPanel();
        lowButtons.setLayout(new FlowLayout());

        button = new JButton("Ball");
        button = setUpButton(button);
        lowButtons.add(button);

        button = new JButton("Circle");
        button = setUpButton(button);
        lowButtons.add(button);

        button = new JButton("Square");
        button = setUpButton(button);
        lowButtons.add(button);

        buttons.add(topButtons);
        buttons.add(lowButtons);
        cp.add(buttons, BorderLayout.PAGE_START);
    }

    private void createAdditionalButtons(){
        JPanel lowButtons = new JPanel();
        lowButtons.setLayout(new FlowLayout());

        JButton button = new JButton("Rotate Left");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons.add(button);

        button = new JButton("Add Trigger");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons.add(button);

        button = new JButton("Rotate Right");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons.add(button);

        cp.add(lowButtons, BorderLayout.PAGE_END);
    }

    private JButton setUpButton(JButton btn){
        btn.setFont(gf);
        btn.setPreferredSize(new Dimension(100,40));
        btn.setMaximumSize(new Dimension(100, 100));
        return btn;
    }


}
