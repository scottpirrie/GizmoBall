package view;

import model.Model;

import javax.swing.*;

public class SaveView {
    private JFrame frame;
    private Model model;

    public SaveView(){
        frame = new JFrame();
        openSaveMenu();
    }

    private void openSaveMenu() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);
        //still requires rest of code
    }
}
