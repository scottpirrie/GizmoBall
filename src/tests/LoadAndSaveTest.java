import src.model.Model;
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
        model.load("src/tests","validTest.giz");
        assertTrue(model.getGizmos().get(0).getName().equals("T"));
    }

    @Test
    public void loadTestGizmoInvalid(){
        model.load("src/tests", "invalidGizmoTest.giz");
        assertTrue(model.getGizmos().isEmpty());
    }

    @Test
    public void saveTestGizmo(){
        model.load("src/tests","validTest.giz");
        model.save("src/tests", "validSaveTest");
        model.clearModel();
        model.load("src/tests","validSaveTest.giz");

        assertTrue(model.getGizmos().size() == 30);
    }

    @After
    public void tearDown(){
        File f = new File("src/tests/validSaveTest.giz");
        f.delete();
    }

}
