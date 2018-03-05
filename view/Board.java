package view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Board extends JPanel implements Observer{

    private static final long serialVersionUID = 1L;
    private int width;
    private int height;
    private int L;
    private boolean isBuildingMode;

    public Board(int w, int h){
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
        // lines go out of the building area
        if(isBuildingMode) {
            for (int i = 0; i < L; i++) {
                g.drawLine(0, i * L, width, i * L);
                g.drawLine(i * L, 0, i * L, height);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        //Check here later

    }
}
