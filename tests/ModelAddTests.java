import model.Model;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
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
    public void addGizmoExtremePosition(){
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
        model.addAbsorber("absorber","A1","0","17","19","17");
        assertTrue(model.getAbsorbers().get(1).getName().equals(name));
    }

    @Test
    public void addAbsorberInvalidName(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A0","0","17","19","17");
        assertTrue(model.getAbsorbers().size() == size);
    }

    @Test
    public void addAbsorberInvalidPosition(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A1","0","19","19","19");
        assertTrue(model.getAbsorbers().size() == size);
    }

    @Test
    public void addAbsorberExtremePosition(){
        int size = model.getAbsorbers().size();
        model.addAbsorber("absorber","A1","-1","21","21","21");
        assertTrue(model.getAbsorbers().size() == size);
    }

    /*
        Add Flipper Tests
     */



}
