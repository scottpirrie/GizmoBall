package controller;

import model.Model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyPressListener implements KeyListener{

    private Model model;

    public KeyPressListener(Model model){
        this.model = model;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_DELETE){
            //Ready Ball for launch here
        }

        if(e.getKeyCode() == KeyEvent.VK_UP){
            model.getFlippers().get(0).setPressed(true);
            model.getFlippers().get(1).setPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_DELETE){
            //Launch ball from absorber here
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) {
            model.getFlippers().get(0).setPressed(false);
            model.getFlippers().get(1).setPressed(false);
        }
    }
}
