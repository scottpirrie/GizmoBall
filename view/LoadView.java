package view;

import model.Model;

import javax.swing.*;

public class LoadView {
    private JFrame frame;
    private Model m;

    public LoadView(Model model){
        m = model;
        frame = new JFrame();
        openLoadMenu();
    }

    private void openLoadMenu(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if(result == JFileChooser.APPROVE_OPTION) {
            boolean isFormatValid = m.load(fileChooser.getCurrentDirectory().toString(), fileChooser.getSelectedFile().getName());
            if(!isFormatValid){
                JOptionPane.showMessageDialog(frame,
                        "The file could not be loaded. Please check its extension and its contents.",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
