import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class LoadAndSaveTest {

    private Model model;

    @Before
    public void setup() {
        model = new Model();

    }

    @Test
    public void loadTestValid(){
        model.load("tests","validTest.giz");
        assertTrue(model.getGizmos().get(0).getName().equals("T"));
    }

    @Test
    public void loadTestGizmoInvalid(){
        model.load("tests", "invalidGizmoTest.giz");
        assertTrue(model.getGizmos().isEmpty());
    }

    @Test
    public void saveTestGizmo(){
        model.load("tests","validTest.giz");
        model.save("tests", "validSaveTest");
        model.clearModel();
        model.load("tests","validSaveTest.giz");

        assertTrue(model.getGizmos().size() == 30);
    }

    @After
    public void tearDown(){
        File f = new File("tests/validSaveTest.giz");
        f.delete();
    }

}
