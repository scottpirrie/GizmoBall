package view;

import controller.RunListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RunGui implements Gui {

    private JFrame frame;
    private Board board;
    private Container cp;
    private Font gf;
    private ActionListener listener;
    private JButton button;
    //private Model model;

    //still need to pass through the model and board
    public RunGui(Board newBoard){
        board = newBoard;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Gizmoball - Run Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        listener = new RunListener(frame, board);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 12);

        cp.add(board, BorderLayout.CENTER);

        createMenuBar();
        createButtons();


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOption;
        JMenuItem menuItem;

        ImageIcon saveIcon = new ImageIcon("save.png");
        ImageIcon quitIcon = new ImageIcon("quit.png");
        ImageIcon loadIcon = new ImageIcon("load.png");
        ImageIcon buildIcon = new ImageIcon("build.png");

        menuOption = new JMenu("Option");

        menuItem = new JMenuItem("Save", saveIcon);
        menuItem.addActionListener(listener);
        menuOption.add(menuItem);

        menuItem = new JMenuItem("Load",loadIcon);
        menuItem.addActionListener(listener);
        menuOption.add(menuItem);

        menuItem = new JMenuItem("Quit", quitIcon);
        menuItem.addActionListener(listener);
        menuOption.add(menuItem);

        menuBar.add(menuOption);


        menuOption = new JMenu("Switch Mode");
            menuItem = new JMenuItem("Build Mode", buildIcon);
            //add the action listener for switching mode
            menuItem.addActionListener(listener);
        menuOption.add(menuItem);
        menuBar.add(menuOption);

        frame.setJMenuBar(menuBar);

    }

    private void createButtons(){
        ImageIcon startIcon = new ImageIcon("start.png");
        ImageIcon stopIcon = new ImageIcon("stop.png");
        ImageIcon tickIcon = new ImageIcon("tick.png");

        //still need the action listeners for the buttons.
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        button = new JButton("Start", startIcon);
        button.setFont(gf);
        button.setMaximumSize(new Dimension(100, 100));
        buttons.add(button);

        button = new JButton("Tick", tickIcon);
        button.setFont(gf);
        button.setMaximumSize(new Dimension(100, 100));
        buttons.add(button);

        button = new JButton("Stop", stopIcon);
        button.setFont(gf);
        button.setMaximumSize(new Dimension(100, 100));
        buttons.add(button);

        cp.add(buttons, BorderLayout.PAGE_END);
    }


}
