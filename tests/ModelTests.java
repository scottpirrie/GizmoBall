package tests;

import model.Model;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ModelTests {
    private Model model;



    @Before
    public void setup() {

        model = new Model();
    }

    @Test
    public void testAddBall(){

        assertTrue(model.addBall("ball","B0", "0.36", "0.44", "0.0", "0.0"));
    }

    @Test
    public void testRemoveBall(){
        //adding ball first
        model.addBall("ball","B0", "0.36", "0.44", "0.0", "0.0");
        //testing for remove ball
        assertTrue(model.remove(0.36,0.44));
    }

    @Test
    public void testAddGizmo(){

        assertTrue(model.addGizmo("circle", "C0", "1", "1"));
    }


    @Test
    public void testRemoveGizmo(){
        model.addGizmo("circle", "C0", "1", "1");
        assertTrue(model.remove(1,1));
    }

    @Test
    public void testGizmoSpaceTaken(){
        model.addGizmo("circle", "C0", "1", "1");
        //adding another gizmo in same place
        assertFalse(model.addGizmo("triangle", "T0", "1", "1"));
    }

    @Test
    public void testRotateGizmo(){
        model.addGizmo("triangle", "T0", "2", "2");
        model.addGizmo("absorber", "A0", "3", "3");
        model.addGizmo("square", "S0", "4", "4");

        model.addGizmo("leftflipper", "LF0", "3", "3");
        model.addGizmo("rightflipper", "RF0", "8", "3");

        //roate Gizmos
    }


    //absorber
    @Test
    public void testAddAbsorber(){
        assertTrue(model.addAbsorber("absorber", "A", "1", "1", "4", "4"));

    }

    @Test
    public void testAddAbsorberSpaceTaken(){
        model.addAbsorber("absorber", "A", "1", "1", "4", "4");

        //adding another absorber in same place
        assertFalse(model.addAbsorber("absorber", "A", "1", "1", "4", "4"));
    }


    @Test
    public void testAbsorberRemove(){
        model.addAbsorber("absorber", "A", "1", "1", "4", "4");
        assertTrue(model.remove(1,1));
    }

    @Test
    public void testAbsorberNotRemove(){
        model.addAbsorber("absorber", "A", "1", "1", "4", "4");
        assertFalse("not a stored absorber", model.remove(5,5));
    }


    //left flippers
    @Test
    public void testAddLeftFlipper(){
        assertTrue(model.addFlipper("leftflipper", "F", "1", "1"));
    }

    @Test
    public void testAddLeftFlipperSpaceTaken(){
        model.addFlipper("leftflipper", "F", "1", "1");

        //adding another flipper in same place
        assertFalse(model.addFlipper("leftflipper", "F", "1", "1"));
    }

    @Test
    public void testLeftFlipperRemove(){
        model.addFlipper("leftflipper", "F", "1", "1");
        assertTrue(model.remove(1,1));
    }

    @Test
    public void testLeftFlipperNotRemove(){
        model.addFlipper("leftflipper", "F", "1", "1");
        assertFalse("not a stored flipper", model.remove(5,5));
    }

    //right flipper
    @Test
    public void testAddRightFlipper(){
        assertTrue(model.addFlipper("rightflipper", "F", "1", "1"));
    }

    @Test
    public void testAddRightFlipperSpaceTaken(){
        model.addFlipper("rightflipper", "F", "1", "1");

        //adding another flipper in same place
        assertFalse(model.addFlipper("rightflipper", "F", "1", "1"));
    }

    @Test
    public void testRightFlipperRemove(){
        model.addFlipper("rightflipper", "F", "1", "1");
        assertTrue(model.remove(1,1));
    }

    @Test
    public void testRightFlipperNotRemove(){
        model.addFlipper("rightflipper", "F", "1", "1");
        assertFalse("not a stored flipper", model.remove(5,5));
    }


}
