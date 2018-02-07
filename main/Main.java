package main;

import javax.swing.UIManager;

import model.*;
import view.RunGui;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();

		model.setBallSpeed(200, 200);

		// Vertical line at (100,100), width 300
		//model.addLine(new VerticalLine(90, 100, 300));
		/*//model.addLine(new VerticalLine(100, 122, 300));
		model.addLine(new VerticalLine(100, 200, 300));
		model.addLine(new VerticalLine(100, 300, 300));
		model.addLine(new VerticalLine(100, 400, 300));*/

		/*model.addSquare(new Square(50,50,20));
		model.addTriangle(new Triangle(100,100,20));
		model.addSquare(new Square(150,150,20));
		model.addTriangle(new Triangle(200,200,20));
		model.addSquare(new Square(250,250,20));
		model.addTriangle(new Triangle(300,300,25));
		model.addSquare(new Square(350,350,20));
		model.addSquare(new Square(400,400,20));

		model.addSquare(new Square(450,450,20));


		model.addCircle(new Circle(325,150,10));
		model.addCircle(new Circle(178,30,15));
		model.addCircle(new Circle(256,123,10));

		/*Triangle triangle = new Triangle(300,300,25);
		model.addTriangle(triangle);
		model.rotate(triangle);*/
		RunGui gui = new RunGui(model);
		gui.createAndShowGUI();
	}
}
