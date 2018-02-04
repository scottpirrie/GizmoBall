package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.*;
import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class Board extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	protected int width;
	protected int height;
	protected Model gm;

	public Board(int w, int h, Model m) {
		// Observe changes in Model
		m.addObserver(this);
		width = w;
		height = h;
		gm = m;
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	// Fix onscreen size
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		// Draw all the vertical lines
		for (VerticalLine vl : gm.getLines()) {
			g2.fillRect(vl.getX(), vl.getY(), vl.getWidth(), 1);
		}

		for(Square square:gm.getSqs()){
			g2.fillRect(square.getxPos(),square.getyPos(),square.getWidth(),square.getWidth());

		}
		for(Triangle triangle:gm.getTrs()){
			LineSegment l1 = triangle.getLines().get(0);
			LineSegment l2 = triangle.getLines().get(1);
			LineSegment l3 = triangle.getLines().get(2);
			int [] xPoints = {(int)l1.p1().x(),(int)l1.p2().x(),(int)l2.p2().x()};
			int [] yPoints = {(int)l1.p1().y(),(int)l1.p2().y(),(int)l2.p2().y()};
			g2.fillPolygon(xPoints,yPoints,3);

		}

		for(Circle circle:gm.getCrs()){
		g2.fillOval(circle.getxPos()-circle.getRadious(),circle.getyPos()-circle.getRadious(),circle.getRadious()*2,circle.getRadious()*2);
		//g2.setColor(Color.RED);
		//g2.fillOval(circle.getxPos(),circle.getyPos(),circle.getRadious(),circle.getRadious());

		}
		
		Ball b = gm.getBall();
		if (b != null) {
			g2.setColor(b.getColour());
			int x = (int) (b.getExactX() - b.getRadius());
			int y = (int) (b.getExactY() - b.getRadius());
			int width = (int) (2 * b.getRadius());
			g2.fillOval(x, y, width, width);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
			repaint();
		}
	
}
