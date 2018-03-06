package view;

import model.AbstractGizmo;
import model.Ball;
import model.Flipper;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Board extends JPanel implements Observer{

    private static final long serialVersionUID = 1L;
    private Model model;
    private int width;
    private int height;
    private int L;
    private boolean isBuildingMode;

    public Board(Model model, int w, int h){
        this.model = model;
        this.width = w;
        this.height = h;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        L=width/20;
        isBuildingMode=false;
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public boolean isBuildingMode() {
        return isBuildingMode;
    }

    public void setBuildingMode(boolean buildingMode) {
        isBuildingMode = buildingMode;
    }

    public void paintComponent(Graphics g) {
        //repaint can make bad systems laggy but smooths painting ball collisions
        repaint();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        // lines go out of the building area
        if(isBuildingMode) {
            for (int i = 0; i < L; i++) {
                g2.drawLine(0, i * L, width, i * L);
                g2.drawLine(i * L, 0, i * L, height);
            }
        }

        //Normal Painting
        for(AbstractGizmo gizmo : model.getGizmos()){

        }

        for(Flipper flipper : model.getFlippers()){

        }

        for(Ball ball : model.getBalls()){

        }


    }

    @Override
    public void update(Observable o, Object arg) {
        validate();
        repaint();
    }
}
