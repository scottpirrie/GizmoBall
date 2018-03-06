package view;

import model.AbstractGizmo;
import model.Ball;
import model.Flipper;
import model.Model;
import physics.LineSegment;

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

    public Model getModel(){
        return model;
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
            if(gizmo.getType().toLowerCase().equals("square")){
                g2.setColor(Color.RED);
                g2.fillRect(gizmo.getxPos()*L,gizmo.getyPos()*L,L,L);

            }else if(gizmo.getType().toLowerCase().equals("triangle")){
                g2.setColor(Color.YELLOW);
                LineSegment l1 = gizmo.getLines().get(0);
                LineSegment l2 = gizmo.getLines().get(1);
                LineSegment l3 = gizmo.getLines().get(2);

                int [] xPoints = {(int)l1.p1().x()*L,(int)l2.p1().x()*L,(int)l3.p1().x()*L};
                int [] yPoints = {(int)l1.p1().y()*L,(int)l2.p1().y()*L,(int)l3.p1().y()*L};
                Polygon p = new Polygon(xPoints,yPoints,3);
                g2.fillPolygon(p);

                g2.fillPolygon(xPoints,yPoints,3);
            }else if(gizmo.getType().toLowerCase().equals("circle")){
                g2.setColor(Color.GREEN);
                g2.fillOval(gizmo.getxPos()*L,gizmo.getyPos()*L,L,L);
            }

        }

    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("painting...");
        validate();
        repaint();
    }
}
