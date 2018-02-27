package view;

import javax.swing.*;

public class LoadView {
    private JFrame frame;
    //private Model model;

    public LoadView(){
        frame = new JFrame();
        openLoadMenu();
    }

    private void openLoadMenu(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        //still requires rest of code;

    }
}
