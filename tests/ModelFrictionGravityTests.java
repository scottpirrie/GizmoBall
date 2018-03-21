import model.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ModelFrictionGravityTests {

    private Model model;

    @Before
    public void setup() {
        model = new Model();

    }

    @Test
    public void checkGravityValid(){

    }

    @After
    public void tearDown(){
        model.clearModel();
    }
}
