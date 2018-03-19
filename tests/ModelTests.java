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



}
