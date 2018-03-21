import model.Model;
import org.junit.Before;
import org.junit.Test;

public class ModelGameLoopTest {

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

    @Test
    public void testGameLoop(){
        model.gameLoop(0.0167);
    }

}
