package gov.tfl.selenium.load;

import com.google.gson.Gson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dev on 11/05/16.
 */
public class SeleniumWebTask implements SeleniumTask {
    private WebDriver driver;
    private List<Step> steps;
    private String testName;
    private String url;
    private long pageWait;
    public SeleniumWebTask(WebDriver driver, String testName, List<Step> steps, String url){
        this.driver = driver;
        this.steps = steps;
        this.testName = testName;
        this.url = url;
    }
    protected void perform(Step step){
        WebElement element = driver.findElement(getBy(step.getIdentifier(), step.getType()));
        switch(step.getAction()){
            case SELECT:{
                Select select = new Select(element);
                select.deselectAll();
                select.selectByVisibleText(step.getValue());
                break;
            }
            case INPUT:{
                element.sendKeys(step.getValue());
                break;
            }
            default:{
                element.click();
            }
        }
        if(step.getWait() > 0){
            driver.manage().timeouts().implicitlyWait(step.getWait(), TimeUnit.MILLISECONDS);
        }
    }
    private By getBy(String id,Type type){
        By by = null;
        switch(type){
            case ID :{
                by = By.id(id);
                break;
            }
            case CSS: {
                by = By.className(id);
                break;
            }
            case TEXT: {
                by = By.linkText(id);
                break;
            }
            case NAME: {
                by = By.name(id);
                break;
            }
            default :{
                by = By.xpath(id);
            }
        }
        return by;
    }
    public void execute(){
        System.out.println("Executing test "+testName);
        System.out.println("Steps "+steps);
        if(null != url) {
            driver.get(url);
            if(getPageWait() > 0)
                driver.manage().timeouts().implicitlyWait(getPageWait(), TimeUnit.MILLISECONDS);
        }
        Step stepA = null;
        try {
            for (Step step : steps) {
                stepA = step;
                this.perform(step);
            }
            assert(false);
        }catch(Exception e){
            assert(false);

            System.out.println("Test "+testName+" Failed.");
            System.out.println("Step data : "+(null != stepA ?stepA.toJson():"is null"));
            System.out.println("Exception is : "+e.getMessage());

        }
    }

    public long getPageWait() {
        return pageWait;
    }

    public void setPageWait(long pageWait) {
        this.pageWait = pageWait;
    }
}
