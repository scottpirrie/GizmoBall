package view;

import model.*;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
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
<<<<<<< HEAD
        this.model.addObserver(this);
=======
>>>>>>> 543827cca5d6df3f0a651a908d34c6a145dcebc0
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
        validate();
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
                double x = gizmo.getxPos();
                double y = gizmo.getyPos();
                double diameter = (0.5 * L ) * 2;
                Ellipse2D circle = new Ellipse2D.Double(x*L,y*L,diameter,diameter);
                g2.fill(circle);
            }
        }

        for(AbsorberGizmo absorber : model.getAbsorbers()){
                int width = Math.abs(absorber.getxPos2() - absorber.getxPos()) * L;
                int height = Math.abs(absorber.getyPos2() - absorber.getyPos()) * L;
                g2.setColor(Color.MAGENTA);
                g2.fillRect(absorber.getxPos()*L,absorber.getyPos()*L, width,height);
        }

        for(Flipper flipper : model.getFlippers()){
            g2.setColor(Color.CYAN);
            g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,1));
            g2.drawLine(flipper.getXPivot()*L,flipper.getYPivot()*L,(int)flipper.getXArc()*L,(int)flipper.getYArc()*L);
        }

        for(Ball ball : model.getBalls()){
            g2.setColor(ball.getColour());
            double x = (ball.getExactX() - ball.getRadius());
            double y = (ball.getExactY() - ball.getRadius());
            double diameter = (ball.getRadius()*L) * 2;
            Ellipse2D.Double ballCircle = new Ellipse2D.Double(x*L,y*L,diameter,diameter);
            g2.fill(ballCircle);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
<<<<<<< HEAD
        System.out.println("Updating");
=======
>>>>>>> 543827cca5d6df3f0a651a908d34c6a145dcebc0
        validate();
        repaint();
    }
}
