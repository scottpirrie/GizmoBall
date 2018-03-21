
import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ModelRemoveTests {

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
        Remove Gizmo Test

     */

    @Test
    public void removeGizmoValid(){
        int size = model.getGizmos().size();
        model.remove(1,1);
        String name = model.findGizmoName(1,1);
        assertTrue(model.getGizmos().size() == (size - 1)
                && name.equals(""));

    }

    @Test
    public void removeGizmoInvalid(){
        int size = model.getGizmos().size();
        model.remove(-1,-1);
        assertTrue(model.getGizmos().size() == size);

    }

    /*
        Remove Absorber Test

     */

    @Test
    public void removeAbsorberValid(){
        int size = model.getAbsorbers().size();
        model.remove(0,19);
        String name = model.findGizmoName(0,19);
        assertTrue(model.getAbsorbers().size() == (size - 1)
                && name.equals(""));

    }

    @Test
    public void removeAbsorberInvalid(){
        int size = model.getAbsorbers().size();
        model.remove(-1,-1);
        assertTrue(model.getAbsorbers().size() == size);

    }

    /*
        Remove Flipper Test

     */

    @Test
    public void removeFlipperValid(){
        int size = model.getFlippers().size();
        model.remove(1,10);
        String name = model.findGizmoName(1,10);
        assertTrue(model.getFlippers().size() == (size - 1)
                && name.equals(""));

    }

    @Test
    public void removeFlipperInvalid(){
        int size = model.getFlippers().size();
        model.remove(-1,-1);
        assertTrue(model.getFlippers().size() == size);

    }

    /*
        Remove Ball Test

     */

    @Test
    public void removeBallValid(){
        int size = model.getBalls().size();
        model.remove(10,1);
        String name = model.findGizmoName(10,1);
        assertTrue(model.getBalls().size() == (size - 1)
                && name.equals(""));

    }

    @Test
    public void removeBallInvalid(){
        int size = model.getBalls().size();
        model.remove(-1,-1);
        assertTrue(model.getBalls().size() == size);

    }

    @After
    public void tearDown(){
        model.clearModel();
    }

}
