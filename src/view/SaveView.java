package src.view;

import src.model.Model;

import javax.swing.*;

public class SaveView {

    private JFrame frame;
    private Model m;
    public SaveView(Model model){
        m = model;
        frame = new JFrame();
        openSaveMenu();
    }

    private void openSaveMenu(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);

        if(result == JFileChooser.APPROVE_OPTION) {
            m.save(fileChooser.getCurrentDirectory().toString(), fileChooser.getSelectedFile().getName());
        }

    }
}
