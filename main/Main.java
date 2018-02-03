package main;

import javax.swing.UIManager;

import model.Circle;
import model.Model;
import model.Square;
import model.VerticalLine;
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

		model.addSquare(new Square(100,100,20));
		model.addSquare(new Square(150,150,20));
		model.addSquare(new Square(200,200,20));
		model.addSquare(new Square(250,250,20));
		model.addSquare(new Square(300,300,20));

		model.addSquare(new Square(400,400,20));


		RunGui gui = new RunGui(model);
		gui.createAndShowGUI();
	}
}
