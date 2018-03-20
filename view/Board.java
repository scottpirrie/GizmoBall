package view;

import model.*;
import physics.LineSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Board extends JPanel implements Observer{

    private static final long serialVersionUID = 1L;
    private Model model;
    private int width;
    private int height;
    private int L;
    private boolean isBuildingMode;
    private List<Point> absorberPoints;

    public Board(Model model, int w, int h){
        this.model = model;
        this.width = w;
        this.height = h;
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.L = width/20;
        isBuildingMode=false;
        absorberPoints = new ArrayList<>();
        this.model.addObserver(this);


    }

    public Model getModel(){
        return model;
    }

    public int getL(){
        return L;
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void setBuildingMode(boolean buildingMode) {
        isBuildingMode = buildingMode;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(absorberPoints.size()>0){
            g2.setColor(Color.MAGENTA);
            for(Point p:absorberPoints){
                g2.fillRect((int)p.getX()*L,(int)p.getY()*L,L,L);
            }
        }

        if(isBuildingMode) {
            g2.setColor(Color.BLACK);
            for (int i = 0; i <= width/L; i++) {
                g2.drawLine(0, i * L, width, i * L);
                g2.drawLine(i * L, 0, i * L, height);

            }
        }

        for(AbstractGizmo gizmo : model.getGizmos()){
            if(gizmo.getType().toLowerCase().equals("square")){
                g2.setColor(gizmo.getColor());
                Rectangle2D rect = new Rectangle2D.Double(gizmo.getxPos()*L,gizmo.getyPos()*L,L,L);
                g2.fill(rect);
            }else if(gizmo.getType().toLowerCase().equals("triangle")){
                g2.setColor(gizmo.getColor());
                LineSegment l1 = gizmo.getLines().get(0);
                LineSegment l2 = gizmo.getLines().get(1);
                LineSegment l3 = gizmo.getLines().get(2);

                int [] xPoints = {(int)Math.round(l1.p1().x()*L),(int)Math.round(l2.p1().x()*L),(int)Math.round(l3.p1().x()*L)};
                int [] yPoints = {(int)Math.round(l1.p1().y()*L),(int)Math.round(l2.p1().y()*L),(int)Math.round(l3.p1().y()*L)};
                Polygon p = new Polygon(xPoints,yPoints,3);
                g2.fillPolygon(p);

                g2.fillPolygon(xPoints,yPoints,3);
            }else if(gizmo.getType().toLowerCase().equals("circle")){
                g2.setColor(gizmo.getColor());
                double x = gizmo.getxPos();
                double y = gizmo.getyPos();
                double diameter = (0.5 * L ) * 2;
                Ellipse2D circle = new Ellipse2D.Double(x*L,y*L,diameter,diameter);
                g2.fill(circle);
            }
        }

        for(AbsorberGizmo absorber : model.getAbsorbers()){
                double width = Math.abs(absorber.getxPos2() - absorber.getxPos()) * L;
                double height = Math.abs(absorber.getyPos2() - absorber.getyPos()) * L;
                Rectangle2D rect = new Rectangle2D.Double(absorber.getxPos()*L,absorber.getyPos()*L,width,height);
                g2.setColor(Color.MAGENTA);
                g2.fill(rect);
        }

        for(Flipper flipper : model.getFlippers()){
            g2.setColor(Color.CYAN);
            g2.setStroke(new BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            double x1 = flipper.getCircles().get(0).getCenter().x()*L;
            double y1 = flipper.getCircles().get(0).getCenter().y()*L;
            double x2 = flipper.getCircles().get(1).getCenter().x()*L;
            double y2 = flipper.getCircles().get(1).getCenter().y()*L;
            Line2D.Double flipperLine = new Line2D.Double(x1,y1,x2,y2);
            g2.setStroke(new BasicStroke(0.5f*L,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            g2.draw(flipperLine);
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

    public void addAbsorberPoints(Point point){
        if(!absorberPoints.contains(point)) {
            absorberPoints.add(point);
        }
    }

    public void clearAbsorberPoints(){
        absorberPoints.clear();
    }

    public List<Point> getAbsorberPoints() {
        return absorberPoints;
    }

    @Override
    public void update(Observable o, Object arg) {
        validate();
        repaint();
    }
}
