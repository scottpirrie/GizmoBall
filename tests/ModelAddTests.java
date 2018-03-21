package tests;

import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelAddTests {

    private Model model;

    @Before
    public void setup() {
        model = new Model();
        model.addGizmo("square","S0","1","1");
        model.addGizmo("square","S1","2","1");
        model.addGizmo("square","S2","3","1");
        model.addGizmo("square","S3","4","1");

        model.addGizmo("triangle","T4","1","2");
        model.addGizmo("triangle","T5","2","2");
        model.addGizmo("triangle","T6","3","2");
        model.addGizmo("triangle","T7","4","2");

        model.addGizmo("circle","C8","1","3");
        model.addGizmo("circle","C9","2","3");
        model.addGizmo("circle","C10","3","3");
        model.addGizmo("circle","C11","4","3");

        model.addAbsorber("absorber","A0","0","19","19","19");

        model.addFlipper("leftflipper","LF0","1","10");
        model.addFlipper("leftflipper","LF1","1","15");
        model.addFlipper("rightflipper","RF2","5","10");
        model.addFlipper("rightflipper","RF3","5","15");

        model.addBall("ball","B0","10","1","0","0");
    }

    /*
        Add Gizmo Tests
     */
    @Test
    public void addGizmoValid(){
        String name = "S12";
        model.addGizmo("square","S12","5","1");
        assertTrue(model.getGizmos().get(12).getName().equals(name));
    }

    @Test
    public void addGizmoInvalidName(){
        int size = model.getGizmos().size();
        model.addGizmo("square","S0","5","1");
        assertTrue(model.getGizmos().size() == size);
    }

    @Test
    public void addGizmoInvalidPosition(){
        int size = model.getGizmos().size();
        model.addGizmo("square","S12","1","1");
        assertTrue(model.getGizmos().size() == size);
    }

    @Test
    public void addGizmoExtremePositionA(){
        int size = model.getGizmos().size();
        model.addGizmo("square","S12","-1","-1");
        assertTrue(model.getGizmos().size() == size);
    }

    @Test
    public void addGizmoExtremePositionB(){
        int size = model.getGizmos().size();
        model.addGizmo("square","S12","21","21");
        assertTrue(model.getGizmos().size() == size);
    }

    /*
        Add Absorber Tests
     */

    @Test
    public void addAbsorberValid(){
        String name = "A1";
        model.addAbsorber("absorber","A1","0","17","19","18");
        assertTrue(model.getAbsorbers().get(1).getName().equals(name));
    }

    @Test
    public void addAbsorberInvalidName(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A0","0","17","19","18");
        assertTrue(model.getAbsorbers().size() == size);
    }

    @Test
    public void addAbsorberInvalidPosition(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A1","0","19","19","19");
        assertTrue(model.getAbsorbers().size() == size);
    }

    @Test
    public void addAbsorberExtremePositionA(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A1","-1","-1","21","21");
        assertTrue(model.getAbsorbers().size() == size);
    }

    @Test
    public void addAbsorberExtremePositionB(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A1","21","21","-1","-1");
        assertTrue(model.getAbsorbers().size() == size);
    }

    /*
        Add Flipper Tests
     */
    @Test
    public void addLeftFlipperValid(){
        String name = "LF4";
        model.addFlipper("leftflipper","LF4","10","10");
        assertTrue(model.getFlippers().get(4).getName().equals(name));
    }

    @Test
    public void addRightFlipperValid(){
        String name = "RF4";
        model.addFlipper("rightflipper","RF4","10","10");
        assertTrue(model.getFlippers().get(4).getName().equals(name));
    }

    @Test
    public void addFlipperInvalidName(){
        int size = model.getFlippers().size();
        model.addFlipper("rightflipper","LF0","10","10");
        assertTrue(model.getFlippers().size() == size);
    }

    @Test
    public void addLeftFlipperInvalidPosition(){
        int size = model.getFlippers().size();
        model.addFlipper("leftflipper","LF4","1","10");
        assertTrue(model.getFlippers().size() == size);
    }

    @Test
    public void addRightFlipperInvalidPosition(){
        //if the size changes, then that indicates that the Flipper has
        //been added to the model Board, which would make this test fail
        int size = model.getFlippers().size();
        model.addFlipper("rightflipper","RF4","5","10");
        assertTrue(model.getFlippers().size() == size);
    }


    @Test
    public void addLeftFlipperExtremePositionA(){
        int size = model.getFlippers().size();
        model.addFlipper("leftflipper","LF4","-1","-1");
        assertTrue(model.getFlippers().size() == size);
    }

    @Test
    public void addRightFlipperExtremePositionA(){
        int size = model.getFlippers().size();
        model.addFlipper("rightflipper","RF4","-1","-1");
        assertTrue(model.getFlippers().size() == size);
    }

    @Test
    public void addLeftFlipperExtremePositionB(){
        int size = model.getFlippers().size();
        model.addFlipper("leftflipper","LF4","21","21");
        assertTrue(model.getFlippers().size() == size);
    }

    @Test
    public void addRightFlipperExtremePositionB(){
        int size = model.getFlippers().size();
        model.addFlipper("rightflipper","RF4","21","21");
        assertTrue(model.getFlippers().size() == size);
    }

    /*
        Add Ball
     */

    @Test
    public void addBallValid(){
        String name = "B1";
        model.addBall("ball","B1","10","2","0","0");
        assertTrue(model.getBalls().get(1).getName().equals(name));
    }

    @Test
    public void addBallInvalidName(){
        //If the Ball is added with a incorrect name then the size of the ball
        //would change casuing the test to fail
        int size = model.getBalls().size();
        model.addBall("ball","B0","2","2","0","0");
        assertTrue(model.getBalls().size() == size);
    }

    @Test
    public void addBallInvalidPosition(){
        int size = model.getBalls().size();
        model.addBall("ball","B1","1","1","0","0");
        assertTrue(model.getBalls().size() == size);
    }

    @Test
    public void addBallExtremePositionA(){
        int size = model.getBalls().size();
        model.addBall("ball","B1","-1","-1","0","0");
        assertTrue(model.getBalls().size() == size);
    }

    @Test
    public void addBallExtremePositionB(){
        int size = model.getBalls().size();
        model.addBall("ball","B1","21","21","0","0");
        assertTrue(model.getBalls().size() == size);
    }

    @After
    public void tearDown(){
        model.clearModel();
    }

}
