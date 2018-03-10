package controller;

import model.Model;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MoveFlipperListener implements KeyListener{

    private Model model;

    public MoveFlipperListener(Model m){
        this.model = m;
    }

    //TODO THIS IS ALL TESTING CODE

    @Override
    public void keyPressed(KeyEvent e) {
        //Up here put something that finds a flipper
        if(e.getKeyCode() == KeyEvent.VK_UP){
            model.getFlippers().get(0).setPressed(true);
            model.getFlippers().get(1).setPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            model.getFlippers().get(0).setPressed(false);
            model.getFlippers().get(1).setPressed(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


}
