package view;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;


public class BuildGui implements Gui {

    private JFrame frame;
    private Board board;
    private Container cp;
    private Font gf;
    private ActionListener listener;

    public BuildGui(Board newBoard){
        board = newBoard;
    }

    public void createAndShowGUI(){
        frame = new JFrame("Gizmoball - Build Mode");
        listener = new BuildListener(frame, board);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        cp = frame.getContentPane();
        gf = new Font("Arial", Font.BOLD, 11);

        createMenuBar();
        createGizmoButtons();
        createAdditionalButtons();

        cp.add(board, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOption;

        ImageIcon saveIcon = new ImageIcon("save.png");
        ImageIcon quitIcon = new ImageIcon("quit.png");
        ImageIcon loadIcon = new ImageIcon("load.png");
        ImageIcon runIcon = new ImageIcon("run.png");

        menuOption = new JMenu("Option");

        JMenuItem saveItem = new JMenuItem("Save", saveIcon);
        saveItem.addActionListener(listener);
        menuOption.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load",loadIcon);
        loadItem.addActionListener(listener);
        menuOption.add(loadItem);

        JMenuItem quitItem = new JMenuItem("Quit", quitIcon);
        quitItem.addActionListener(listener);
        menuOption.add(quitItem);
        menuBar.add(menuOption);


        menuOption = new JMenu("Switch Mode");
        JMenuItem runItem = new JMenuItem("Run Mode",runIcon);
        runItem.addActionListener(listener);
        menuOption.add(runItem);
        menuBar.add(menuOption);

        frame.setJMenuBar(menuBar);
    }

    private void createGizmoButtons(){
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,0));

        JPanel topButtons = new JPanel();
        topButtons.setLayout(new FlowLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JButton triangleButton = new JButton("Triangle");
        triangleButton = setUpButton(triangleButton);
        triangleButton.setName("Triangle");
        triangleButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        topButtons.add(triangleButton);

        JButton lFlipperButton = new JButton("Left Flipper");
        lFlipperButton = setUpButton(lFlipperButton);
        lFlipperButton.setName("leftFlipper");
        lFlipperButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        topButtons.add(lFlipperButton);

        JButton rFlipperButton = new JButton("Right Flipper");
        rFlipperButton = setUpButton(rFlipperButton);
        rFlipperButton.setName("rightFlipper");
        rFlipperButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        topButtons.add(rFlipperButton);

        JButton absorberButton = new JButton("Absorber");
        absorberButton = setUpButton(absorberButton);
        absorberButton.setName("absorber");
        absorberButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        topButtons.add(absorberButton);

        JPanel lowButtons = new JPanel();
        lowButtons.setLayout(new FlowLayout());

        JButton ballButton = new JButton("Ball");
        ballButton = setUpButton(ballButton);
        ballButton.setName("ball");
        ballButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons.add(ballButton);

        JButton circleButton = new JButton("Circle");
        circleButton = setUpButton(circleButton);
        circleButton.setName("Circle");
        circleButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons.add(circleButton);

        JButton squareButton = new JButton("Square");
        squareButton = setUpButton(squareButton);
        squareButton.setName("Square");
        squareButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons.add(squareButton);

        JButton removeButton = new JButton("Remove");
        removeButton = setUpButton(removeButton);
        removeButton.setName("Remove");
        removeButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons.add(removeButton);

        buttons.add(topButtons);
        buttons.add(lowButtons);

        JPanel tabGizmoObj = new JPanel();
        JPanel tabGizmoSet = new JPanel();

        tabGizmoObj.add(buttons);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridLayout(2,1));

        JPanel settingsPanel1 = new JPanel();
        settingsPanel1.setLayout(new FlowLayout());

        JSlider gravitySlider = createNewSlider();
        gravitySlider.addMouseListener(new SetGravityConstantListener(board.getModel()));
        JLabel label = new JLabel("Gravity: ");
        settingsPanel1.add(label);
        settingsPanel1.add(gravitySlider);

        JPanel settingsPanel2 = new JPanel();
        settingsPanel1.setLayout(new FlowLayout());

        JSlider frictionSlider = createNewSlider();
        frictionSlider.addMouseListener(new SetFrictionConstantListener(board.getModel()));
        JLabel label2 = new JLabel("Friction: ");
        settingsPanel2.add(label2);
        settingsPanel2.add(frictionSlider);

        settingsPanel.add(settingsPanel1);
        settingsPanel.add(settingsPanel2);

        tabGizmoSet.add(settingsPanel);

        tabbedPane.addTab("Gizmoball Objects", tabGizmoObj);
        tabbedPane.addTab("Gravity & Friction", tabGizmoSet);

        cp.add(tabbedPane, BorderLayout.NORTH);
    }

    private JSlider createNewSlider(){
        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,200,100);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
//change sliders
        Hashtable<Integer, JLabel> pos = new Hashtable<>();
        pos.put(0, new JLabel("0"));

        pos.put(50, new JLabel("50"));

        pos.put(100, new JLabel("100"));

        pos.put(150, new JLabel("150"));

        pos.put(200, new JLabel("200"));

        slider.setLabelTable(pos);

        return slider;
    }

    private void createAdditionalButtons(){
        JPanel lowButtons1 = new JPanel();
        lowButtons1.setLayout(new FlowLayout());

        JPanel lowButtons2 = new JPanel();
        lowButtons2.setLayout(new FlowLayout());

        JButton button = new JButton("Rotate");
        button = setUpButton(button);
        button.setName("Remove");
        button.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));

        lowButtons1.add(button);

        JButton addTrigger = new JButton("Add Trigger");
        addTrigger = setUpButton(addTrigger);
        addTrigger.setPreferredSize(new Dimension(120,40));
        addTrigger.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons2.add(addTrigger);

        JButton addKeyBind = new JButton("Add KeyBind");
        addKeyBind = setUpButton(addKeyBind);
        addKeyBind.setPreferredSize(new Dimension(120,40));
        addKeyBind.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons2.add(addKeyBind);

        JButton removeTrigger = new JButton("Remove Trigger");
        removeTrigger = setUpButton(removeTrigger);
        removeTrigger.setPreferredSize(new Dimension(120,40));
        removeTrigger.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons1.add(removeTrigger);

        JButton removeKeyBind = new JButton("Remove KeyBind");
        removeKeyBind = setUpButton(removeKeyBind);
        removeKeyBind.setPreferredSize(new Dimension(120,40));
        removeKeyBind.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons2.add(removeKeyBind);

        JButton moveButton = new JButton("Move");
        moveButton = setUpButton(moveButton);
        moveButton.setName("Move");
        moveButton.addActionListener(new SwitchPanelListener(board.getModel(),board.getL(),board));
        lowButtons1.add(moveButton);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2,3));
        buttons.add(lowButtons1);
        buttons.add(lowButtons2);

        cp.add(buttons, BorderLayout.SOUTH);

    }

    private JButton setUpButton(JButton btn){
        btn.setFont(gf);
        btn.setPreferredSize(new Dimension(100,40));
        btn.setMaximumSize(new Dimension(100, 40));
        return btn;
    }


}
