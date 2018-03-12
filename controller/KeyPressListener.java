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

    //TODO this class is really just searching through a map of keys
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyChar();
        if(model.getKeyDownMap().containsKey(key)){
            model.keybindAction(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyChar();
        if(model.getKeyUpMap().containsKey(key)){
            model.keybindAction(key);
        }
    }
}
