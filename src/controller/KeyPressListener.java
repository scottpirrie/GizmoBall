package src.controller;

import src.model.Model;

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
        int key = e.getKeyCode();
        model.changeFlipperStatus(key);
        model.keybindAction(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        model.changeFlipperStatus(key);
        model.keybindAction(key);

    }
}
