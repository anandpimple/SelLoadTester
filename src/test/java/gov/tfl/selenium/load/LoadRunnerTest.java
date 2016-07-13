package gov.tfl.selenium.load;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dev on 12/07/16.
 */
public class LoadRunnerTest {
    @Test
    public void getConfig() throws Exception {
        LoadRunner runner = new LoadRunner(LoadRunnerTest.class.getResource("/test.json").getPath());
        Config config = runner.getConfig();
        gov.tfl.selenium.load.Test test1 = runner.getTest();
        System.out.println(config.getBaseUrl());
        System.out.println(test1.getBefore().get(0).getName());
        System.out.println(test1.getBefore().get(0).getType());
        System.out.println(test1.getBefore().get(0).getSteps());
    }

}