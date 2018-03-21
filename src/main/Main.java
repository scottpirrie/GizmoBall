package src.main;


import src.model.Model;
import src.view.GizmoView;

/**
 * The main will run the Gizmoball View, which will be the main menu
 */

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        GizmoView mainView = new GizmoView(model);
    }
}
