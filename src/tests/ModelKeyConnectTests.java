
import src.model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelKeyConnectTests {

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
        KeyConnect Tests
     */

    @Test
    public void addKeyConnectValid(){
        model.addKeyBind(97,"LF0");
        assertTrue(model.getKeyDownMap().get(97).get(0).equals("LF0"));
    }

    @Test
    public void addKeyConnectExisting(){
        model.addKeyBind(97,"LF0");
        model.addKeyBind(97,"LF1");
        assertTrue(model.getKeyDownMap().get(97).size() == 2);
    }

    @Test
    public void addKeyConnectInvalid(){
        model.addKeyBind(97,"LF999");
        assertTrue(model.getKeyDownMap().size() == 0);
    }

    @Test
    public void removeKeyConnectValid(){
        model.addKeyBind(97,"LF0");
        model.addKeyBind(97,"LF1");

        int size = model.getKeyDownMap().get(97).size();

        model.removeKeybind(97,"LF1");
        assertTrue(model.getKeyDownMap().get(97).size() == (size-1));
    }

    @Test
    public void removeKeyConnectInvalid(){
        model.addKeyBind(97,"LF0");
        model.addKeyBind(97,"LF1");

        int size = model.getKeyDownMap().get(97).size();

        model.removeKeybind(97,"LF999");
        assertTrue(model.getKeyDownMap().get(97).size() == size);
    }


    @Test
    public void removeKeyConnectNameOnlyValid(){
        model.addKeyBind(97,"LF0");
        int size = model.getKeyDownMap().get(97).size();
        model.remove(1,10);
        assertTrue(model.getKeyDownMap().get(97).size() == (size-1));
    }

    @After
    public void tearDown(){
        model.clearModel();
    }

}
