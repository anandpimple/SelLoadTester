package gov.tfl.selenium.load;

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
public class SeleniumWebJob implements Callable<String>, Jsonable {
    private WebDriver driver;
    private Config config;
    private String action;
    private SeleniumTask startTask, endTask;
    private List<SeleniumTask> repetableTask;
    private final static Logger logger = Logger.getLogger(LoadRunner.class.getName());
    private int maxRepeat = 1;
    private String url;
    public SeleniumWebJob(Config config) throws IOException {
        this.config = config;
        driver = this.config.isBackground()?new HeadlessScreenshotDriver():new FirefoxDriver();
    }
    public void setAction(String action){
        this.action=action;
    }
    public WebDriver getDriver(){
        return driver;
    }
    public void destroy(){
        driver.close();
    }
    public String call() throws Exception {
        try {
            driver.navigate().to(config.getBaseUrl()+(null != this.getUrl() && this.getUrl().trim().length() > 0?"/"+this.getUrl():""));
            driver.manage().timeouts().implicitlyWait(config.getPageWait(), TimeUnit.MILLISECONDS);
            int taskRepetaed = 0;
            if(null != startTask){
                logger.info("Running start task ");
                startTask.execute();
                if(startTask.hasError()){
                    System.out.println("Start task failed");
                    throw new Exception("Start task failed");
                }
            }
            while (!config.shouldTerminate()) {
                if(maxRepeat <= 0 || maxRepeat < taskRepetaed) {
                    for (SeleniumTask task : repetableTask) {
                        task.execute();

                    }
                    logger.info("Task repeated for "+taskRepetaed);
                    ++taskRepetaed;
                }

            }
            if(null != endTask){
                logger.info("Running start task ");
                endTask.execute();
            }
        }finally{
            driver.close();
        }
        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SeleniumTask getStartTask() {
        return startTask;
    }

    public void setStartTask(SeleniumTask startTask) {
        this.startTask = startTask;
    }

    public SeleniumTask getEndTask() {
        return endTask;
    }

    public void setEndTask(SeleniumTask endTask) {
        this.endTask = endTask;
    }

    public List<SeleniumTask> getRepetableTask() {
        return repetableTask;
    }

    public void setRepetableTask(List<SeleniumTask> repetableTask) {
        this.repetableTask = repetableTask;
    }
}
