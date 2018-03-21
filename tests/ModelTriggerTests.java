import model.AbsorberGizmo;
import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertTrue;

public class ModelTriggerTests {

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
        Trigger Tests
     */

    @Test
    public void addValidTrigger(){
        model.addTrigger("S0","LF0");
        assertTrue(model.getTriggers().get("S0").contains("LF0"));
    }

    @Test
    public void addToExistingTrigger(){
        model.addTrigger("S0","LF0");
        model.addTrigger("S0","LF1");
        assertTrue(model.getTriggers().get("S0").size() == 2);
    }

    @Test
    public void addInvalidTrigger(){
        model.addTrigger("S0","LF0");
        model.addTrigger("S0","LF0");
        assertTrue(model.getTriggers().get("S0").size() == 1);
    }

    @Test
    public void removeValidTrigger(){
        model.addTrigger("S0","LF0");
        model.addTrigger("S1","LF0");
        model.addTrigger("S2","LF0");
        model.addTrigger("S3","LF0");

        int sizeOfList = model.getTriggers().get("S3").size();

        model.removeTrigger("S3","LF0");
        assertTrue(model.getTriggers().get("S3").size() == (sizeOfList-1));
    }

    @Test
    public void removeInvalidTriggerA(){
        model.addTrigger("S0","LF0");
        model.addTrigger("S1","LF0");
        model.addTrigger("S2","LF0");
        model.addTrigger("S3","LF0");

        int sizeOfList = model.getTriggers().size();
        model.removeTrigger("T7","LF0");
        assertTrue(model.getTriggers().size() == sizeOfList);
    }

    @Test
    public void removeInvalidTriggerB(){
        model.addTrigger("S0","LF0");
        model.addTrigger("S1","LF0");
        model.addTrigger("S2","LF0");
        model.addTrigger("S3","LF0");

        int sizeOfList = model.getTriggers().size();
        model.removeTrigger("S0","LF1");
        assertTrue(model.getTriggers().size() == sizeOfList);
    }


    /*
        KeyBind Actions
     */

    @Test
    public void callGizmoKeyActionValid(){
        model.addKeyBind(97,"S0");
        model.keybindAction(97);
        assertTrue(model.getGizmos().get(0).getColor().equals(Color.GREEN));
    }

    @Test
    public void callFlipperKeyActionValid(){
        model.addKeyBind(97,"LF0");
        model.changeFlipperStatus(97);
        model.keybindAction(97);
        assertTrue(model.getFlippers().get(0).isPressed());
    }

    @Test
    public void callAbsorberKeyActionValid(){
        model.addKeyBind(97,"A0");
        model.getAbsorbers().get(0).captureBall(model.getBalls().get(0));
        model.keybindAction(97);
        assertTrue(model.getAbsorbers().get(0).getBall() == null);
    }

    /*
        Collision Actions Test
     */

    @Test
    public void callGizmoCollisionAction(){

    }

    @After
    public void tearDown(){
        model.clearModel();
    }
}
