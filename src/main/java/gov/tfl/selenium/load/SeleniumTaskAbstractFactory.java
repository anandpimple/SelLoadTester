package gov.tfl.selenium.load;

import java.io.FileNotFoundException;

/**
 * Created by dev on 13/07/16.
 */
public class SeleniumTaskAbstractFactory {
    public static SeleniumTask getTask(TestTask task) throws FileNotFoundException {
        return new SeleniumWebTask(task);
    }
}
