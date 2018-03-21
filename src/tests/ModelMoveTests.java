

import src.model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelMoveTests {

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

        model.addAbsorber("absorber","A0","0","19","20","20");

        model.addFlipper("leftflipper","LF0","1","5");
        model.addFlipper("leftflipper","LF1","1","10");
        model.addFlipper("rightflipper","RF2","5","5");
        model.addFlipper("rightflipper","RF3","5","10");

        model.addBall("ball","B0","10","1","0","0");
    }

    /*
        Move Gizmo Test
     */

    @Test
    public void moveGizmoValid(){
        String nameOldPos = model.findGizmoName(1,3);
        model.moveGizmo(nameOldPos,"10","5");
        assertTrue(model.findGizmoName(10,5).equals(nameOldPos));
    }

    @Test
    public void moveGizmoInvalid(){
        String nameOldPos = model.findGizmoName(1,3);
        String nameOtherGizmoPos = "T6";
        model.moveGizmo(nameOldPos,"3","2");
        assertTrue(model.findGizmoName(3,2).equals(nameOtherGizmoPos) && model.findGizmoName(1,3).equals(nameOldPos));
    }

    /*
        Move Absorber Test
     */

    @Test
    public void moveAbsorberValid(){
        String nameOldPos = model.findGizmoName(0,19);
        model.moveAbsorber("A0","0","17","20","19");
        assertTrue(model.findGizmoName(0,17).equals(nameOldPos));
    }

    @Test
    public void moveAbsorberInvalid(){
        String nameOldPos = model.findGizmoName(0,19);
        model.moveAbsorber("A0","1","1","4","3");
        assertTrue(model.findGizmoName(0,19).equals(nameOldPos));
    }

    /*
        Move Flipper Test
     */

    @Test
    public void moveFlipperValid(){
        String nameOldPos = model.findGizmoName(1,5);
        model.moveFlipper("LF0","11","15");
        assertTrue(model.findGizmoName(11,15).equals(nameOldPos));
    }

    @Test
    public void moveFlipperInvalid(){
        String nameOldPos = model.findGizmoName(1,5);
        String nameOtherGizmoPos = "LF1";
        model.moveFlipper("LF0","1","10");
        assertTrue(model.findGizmoName(1,5).equals(nameOldPos) && model.findGizmoName(1,10).equals(nameOtherGizmoPos));
    }

    /*
        Move Ball Test
     */

    @Test
    public void moveBallValid(){
        String nameOldPos = model.findGizmoName(10,1);
        model.moveBall("B0","10","7");
        System.out.println(model.findGizmoName(10,7));
        assertTrue(model.findGizmoName(10,7).equals(nameOldPos));
    }

    @Test
    public void moveBallInvalid(){
        String nameOldPos = model.findGizmoName(10,1);
        model.moveBall("B0","1","1");
        assertTrue(model.findGizmoName(10,1).equals(nameOldPos));
    }

    @After
    public void tearDown(){
        model.clearModel();
    }
}
