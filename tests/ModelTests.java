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


    //triggers
    @Test
    public void testAddTriggersGizmos(){
        model.addGizmo("square", "S0", "4", "4");
        model.addGizmo("circle", "C0", "1", "1");

        assertTrue(model.addTrigger("S0", "C0"));
    }

    @Test
    public void testAddTriggersFlippers(){
        model.addFlipper("rightflipper", "F1", "1", "1");
        model.addFlipper("rightflipper", "F2", "5", "5");

        assertTrue(model.addTrigger("F1", "F2"));
    }

    @Test
    public void testAddTriggersAbsorbers(){
        model.addAbsorber("absorber", "A1", "1", "1", "4", "4");
        model.addAbsorber("absorber", "A2", "6", "6", "9", "9");
        assertTrue(model.addTrigger("A1", "A2"));
    }

    @Test
    public void testAddTriggersExtra(){
        model.addAbsorber("absorber", "A1", "1", "1", "4", "4");
        model.addAbsorber("absorber", "A2", "6", "6", "9", "9");

        model.addAbsorber("absorber", "A3", "8", "8", "3", "3");

        model.addTrigger("A1", "A2");

        assertTrue(model.addTrigger("A1", "A3"));
    }

    @Test
    public void testAddTriggersNoGizmo(){
        model.addGizmo("square", "S0", "4", "4");

        assertFalse(model.addTrigger("S0", "Unknown"));
    }


    @Test
    public void testRemoveTriggers(){

        model.addGizmo("square", "S0", "4", "4");
        model.addGizmo("circle", "C0", "1", "1");

        model.addTrigger("S0", "C0");
        assertTrue(model.removeTrigger("S0", "C0"));
    }

    @Test
    public void testRemoveTriggersNoGizmo(){
        model.addGizmo("square", "S0", "4", "4");
        model.addGizmo("circle", "C0", "1", "1");

        assertFalse(model.removeTrigger("S0", "C0"));
    }

    @Test
    public void testRemoveTriggersWrongTrigger(){
        model.addGizmo("square", "S0", "4", "4");
        model.addGizmo("circle", "C0", "1", "1");

        model.addTrigger("S0", "C0");

        assertFalse(model.removeTrigger("S0", "C1"));
    }

    //move gizmo
    @Test
    public void testMoveGizmo(){
        model.addGizmo("square", "S0", "4", "4");

        assertTrue(model.moveGizmo("S0", "5", "6"));
    }

    @Test
    public void testMoveGimzoNoGizmo(){
        model.addGizmo("square", "S0", "4", "4");

        assertFalse(model.moveGizmo("S1", "5", "7"));
    }

    @Test

    public void testMoveSpaceTaken(){
        model.addGizmo("square", "S0", "4", "4");

        model.addGizmo("circle", "C0", "5", "8");

        assertFalse(model.moveGizmo("S0", "5", "8"));
    }


    //move absorber
    @Test
    public void testMoveAbsorber(){
        model.addAbsorber("absorber", "A1", "1", "1", "4", "4");

        assertTrue(model.moveAbsorber("A1", "5", "6", "8", "8"));
    }

    @Test
    public void testMoveAbsorberNoAbsorber(){
        model.addAbsorber("absorber", "A1", "1", "1", "4", "4");

        assertFalse(model.moveAbsorber("Random", "5", "6", "8", "8"));
    }

    @Test
    public void testMoveAbsorberSpaceTaken(){
        model.addAbsorber("absorber", "A1", "1", "1", "4", "4");

        model.addAbsorber("absorber", "A2", "8", "5", "6", "3");

        assertFalse(model.moveAbsorber("A1", "8", "5", "6", "3"));
    }

    //move right flipper
    @Test
    public void testMoveRightFlipper(){
        model.addFlipper("rightflipper", "F1", "1", "1");

        assertTrue(model.MoveFlipper("F1", "5", "6"));
    }

    @Test
    public void testMoveRightFlipperNoFlipper(){
        model.addFlipper("rightflipper", "F1", "1", "1");

        assertFalse(model.MoveFlipper("Random", "5", "6"));
    }

    @Test
    public void testMoveRightFlipperSpaceTaken(){
        model.addFlipper("rightflipper", "F1", "1", "1");


        model.addFlipper("rightflipper", "F2", "5", "5");


        assertFalse(model.MoveFlipper("F1", "5", "5"));
    }

    //move left flipper
    @Test
    public void testMoveLeftFlipper(){
        model.addFlipper("leftflipper", "F1", "1", "1");

        assertTrue(model.MoveFlipper("F1", "5", "6"));
    }

    @Test
    public void testMoveLeftFlipperNoFlipper(){
        model.addFlipper("leftflipper", "F1", "1", "1");

        assertFalse(model.MoveFlipper("Random", "5", "6"));
    }

    @Test
    public void testMoveLeftFlipperSpaceTaken(){
        model.addFlipper("leftflipper", "F1", "1", "1");


        model.addFlipper("leftflipper", "F2", "5", "5");


        assertFalse(model.MoveFlipper("F1", "5", "5"));
    }


}
