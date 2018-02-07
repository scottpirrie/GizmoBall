package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;
import model.Model;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class RunListener implements ActionListener {

	private Timer timer;
	private Model model;

	public RunListener(Model m) {
		model = m;
		timer = new Timer(50, this);
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {

		if (e.getSource() == timer) {
			model.moveBall(0.05);
		} else
			switch (e.getActionCommand()) {
			case "Start":
				timer.start();
				break;
			case "Stop":
				timer.stop();
				break;
			case "Tick":
				model.moveBall(0.05);
				break;
			case "Quit":
				System.exit(0);
				break;
			case "Save":
				try {
					model.save("test.txt");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
				case "Load":
					try {
						model.load("test.txt");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
			}
	}
}
