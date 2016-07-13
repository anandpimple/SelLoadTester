package gov.tfl.selenium.load;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.google.gson.Gson;
import gov.tfl.selenium.HeadlessScreenshotDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static gov.tfl.selenium.load.LoadRunner.getDataAsString;

/**
 * Created by User on 4/11/2016.
 */
public class SeleniumJobInvoker implements Callable<String>{
    private Config config;
    private final static Logger logger = Logger.getLogger(LoadRunner.class.getName());
    private WebDriver driver;
    private Test test;
    private long startTime;
    public SeleniumJobInvoker(Config config,Test test) throws IOException {
        this.config = config;
        this.test = test;
        driver = this.config.isVisibile()?new HtmlUnitDriver():new FirefoxDriver();
    }
    public void finalize(){
        if(driver != null)
            driver.close();
    }
    private void navigate(String url) {
        if(null != url) {
            driver.navigate().to(url);
            if (config.getDefaultPageWait() > 0)
                driver.manage().timeouts().pageLoadTimeout(config.getDefaultPageWait(), TimeUnit.SECONDS);
        }

    }
    public String call() throws Exception {
        try {
            startTime = System.currentTimeMillis();
            navigate(config.getBaseUrl());
            executeTasks(test.getBefore());
            if(test.getContineous() != null && !test.getContineous().isEmpty()) {
                while(config.getRunTime() > (System.currentTimeMillis() - startTime) ) {
                    executeTasks(test.getContineous());
                }
            }
            executeTasks(test.getAfter());
        }finally{
            driver.close();
            driver = null;
        }
        return null;
    }

    private void executeTasks(List<TestTask> tasks) throws FileNotFoundException {
        if(null != tasks){
            for(TestTask task : tasks){
                logger.info("Executing task "+task.getName()+". Thread is "+this.hashCode());
                SeleniumTaskAbstractFactory.getTask(task).execute();
            }
        }
    }

}
