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
        ImageIcon saveIcon = new ImageIcon(getClass().getResource(
                "/save.png"));

        ImageIcon quitIcon = new ImageIcon(getClass().getResource(
                "/quit.png"));

        ImageIcon loadIcon = new ImageIcon(getClass().getResource(
                "/load.png"));
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOption;
        JMenuItem menuItem;

        menuOption = new JMenu("Option");
            menuItem = new JMenuItem(saveIcon);
            //add the action listener for Saving
            menuOption.add(menuItem);
            menuItem = new JMenuItem(loadIcon);
            //add the action listener for Loading
            menuOption.add(menuItem);
            menuItem = new JMenuItem(quitIcon);
            //add the action listener for Quit
        menuBar.add(menuOption);
        menuOption.add(menuItem);


        menuOption = new JMenu("Switch Mode");
            menuItem = new JMenuItem("Build Mode");
            //add the action listener for switching mode
            menuItem.addActionListener(listener);
        menuOption.add(menuItem);
        menuBar.add(menuOption);

        frame.setJMenuBar(menuBar);

    }

    private void createButtons(){
        ImageIcon startIcon = new ImageIcon(getClass().getResource(
                "/start.png"));

        ImageIcon stopIcon = new ImageIcon(getClass().getResource(
                "/stop.png"));
        //still need the action listeners for the buttons.
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton button1 = new JButton(startIcon);
        button1.setFont(gf);
        button1.setMaximumSize(new Dimension(100, 100));
        buttons.add(button1);

        JButton button2 = new JButton(stopIcon);
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
