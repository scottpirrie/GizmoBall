package view;

import controller.RunListener;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RunGui implements Gui {

    private JFrame frame;
    private Board board;
    private Container cp;
    private Font gf;
    private ActionListener listener;

    //still need to check if anything else needs passed...
    public RunGui(Board newBoard){
        this.board = newBoard;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Gizmoball - Run Mode");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        listener = new RunListener(frame,board);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 12);

        cp.add(board, BorderLayout.CENTER);

        createMenuBar();
        createButtons();

        frame.pack();
        frame.setResizable(false);
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

        JButton startButton = new JButton("Start", startIcon);
        startButton.setFont(gf);
        startButton.setMaximumSize(new Dimension(100, 100));
        startButton.addActionListener(listener);
        buttons.add(startButton);

        JButton tickButton = new JButton("Tick", tickIcon);
        tickButton.setFont(gf);
        tickButton.setMaximumSize(new Dimension(100, 100));
        tickButton.addActionListener(listener);
        buttons.add(tickButton);

        JButton stopButton = new JButton("Stop", stopIcon);
        stopButton.setFont(gf);
        stopButton.setMaximumSize(new Dimension(100, 100));
        stopButton.addActionListener(listener);
        buttons.add(stopButton);

        cp.add(buttons, BorderLayout.PAGE_END);
    }


}
