package view;

import controller.BuildListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;


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

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 12);

        createMenuBar();
        createGizmoButtons();
        createAdditionalButtons();

        cp.add(board, BorderLayout.CENTER);

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
        ImageIcon runIcon = new ImageIcon("run.png");

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
        menuItem = new JMenuItem("Run Mode",runIcon);
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

        //creating a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        JButton button = new JButton("Triangle");
        button = setUpButton(button);
        topButtons.add(button);

        button = new JButton("Left Flipper");
        button = setUpButton(button);
        topButtons.add(button);

        button = new JButton("Right Flipper");
        button = setUpButton(button);
        topButtons.add(button);

        button = new JButton("Absorber");
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

        //creating 1st tab panel
        JPanel tabGizmoObj = new JPanel(){
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 50;
                return size;
            }
        };

        JPanel tabGizmoSet = new JPanel(){
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };

        tabGizmoObj.add(buttons);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2,1));

        JPanel settingsPanel1 = new JPanel();
        settingsPanel1.setLayout(new FlowLayout());

        //need to add action listeners to the sliders
        JSlider gravitySlider = createNewSlider();
        JLabel label = new JLabel("Gravity: ");
        settingsPanel1.add(label);
        settingsPanel1.add(gravitySlider);


        JPanel settingsPanel2 = new JPanel();
        settingsPanel1.setLayout(new FlowLayout());

        JSlider frictionSlider = createNewSlider();
        label = new JLabel("Friction: ");

        settingsPanel2.add(label);
        settingsPanel2.add(frictionSlider);

        settingsPanel.add(settingsPanel1);
        settingsPanel.add(settingsPanel2);

        tabGizmoSet.add(settingsPanel);





        tabbedPane.addTab("Gizmoball Objects", tabGizmoObj);
        tabbedPane.addTab("Gravity & Friction", tabGizmoSet);

        cp.add(tabbedPane, BorderLayout.PAGE_START);
    }

    private JSlider createNewSlider(){
        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Hashtable pos = new Hashtable();
        // Add positions label in the slider
        pos.put(0, new JLabel("0"));
        pos.put(25, new JLabel("25"));
        pos.put(50, new JLabel("50"));
        pos.put(75, new JLabel("75"));
        pos.put(100, new JLabel("100"));

        // Set the label to be drawn
        slider.setLabelTable(pos);

        return slider;
    }

    private void createAdditionalButtons(){
        JPanel lowButtons1 = new JPanel();
        lowButtons1.setLayout(new FlowLayout());

        JPanel lowButtons2 = new JPanel();
        lowButtons2.setLayout(new FlowLayout());

        JButton button = new JButton("Delete Gizmo");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons1.add(button);

        button = new JButton("Rotate Left");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons1.add(button);

        button = new JButton("Rotate Right");
        button = setUpButton(button);
        button.setEnabled(false);
        lowButtons1.add(button);

        button = new JButton("Add Trigger");
        button = setUpButton(button);
        button.setPreferredSize(new Dimension(120,40));
        button.setEnabled(false);
        lowButtons2.add(button);

        button = new JButton("Remove Trigger");
        button = setUpButton(button);
        button.setPreferredSize(new Dimension(120,40));
        button.setEnabled(false);
        lowButtons2.add(button);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,3));
        buttons.add(lowButtons2);
        buttons.add(lowButtons1);



        cp.add(buttons, BorderLayout.PAGE_END);
    }

    private JButton setUpButton(JButton btn){
        btn.setFont(gf);
        btn.setPreferredSize(new Dimension(100,40));
        btn.setMaximumSize(new Dimension(100, 100));
        return btn;
    }


}
