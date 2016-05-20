package gov.tfl.selenium.load;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by dev on 12/05/16.
 */
public class StepTest {
    private static final String OUTPUT = "{\"stepName\":\"First step\",\"wait\":0}";
    @Test
    public void testToString() throws Exception {
        Step step = new Step();
        step.setStepName("First step");
        assertEquals(OUTPUT,step.toJson());
    }
}