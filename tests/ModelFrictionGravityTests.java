import model.Model;
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
        model.setGravityConstant(100);
    }

}
