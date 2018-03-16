package view;

import controller.KeyPressListener;
import controller.MagicKeyListener;
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

    public RunGui(Board newBoard){
        this.board = newBoard;
    }

    public void createAndShowGUI() {
        frame = new JFrame("Gizmoball - Run Mode");
        frame.setPreferredSize(new Dimension(506,604));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(false);
        listener = new RunListener(frame,board);

        cp = frame.getContentPane();
        cp.setFocusable(true);
        cp.addKeyListener(new MagicKeyListener(new KeyPressListener(board.getModel())));
        gf = new Font("Arial", Font.BOLD, 12);

        cp.add(board, BorderLayout.CENTER);

        createMenuBar();
        createButtons();

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        System.out.println("Run Gui: "+frame.getWidth()+" "+frame.getHeight());
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
        startButton.setFocusable(false);
        buttons.add(startButton);

        JButton tickButton = new JButton("Tick", tickIcon);
        tickButton.setFont(gf);
        tickButton.setMaximumSize(new Dimension(100, 100));
        tickButton.addActionListener(listener);
        tickButton.setFocusable(false);
        buttons.add(tickButton);

        JButton stopButton = new JButton("Stop", stopIcon);
        stopButton.setFont(gf);
        stopButton.setMaximumSize(new Dimension(100, 100));
        stopButton.addActionListener(listener);
        stopButton.setFocusable(false);
        buttons.add(stopButton);

        cp.add(buttons, BorderLayout.PAGE_END);
    }


}
