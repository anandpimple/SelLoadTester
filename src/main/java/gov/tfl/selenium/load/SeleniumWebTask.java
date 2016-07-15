package gov.tfl.selenium.load;

import com.google.gson.Gson;
import gov.tfl.selenium.HeadlessScreenshotDriver;
import gov.tfl.selenium.data.FileData;
import gov.tfl.selenium.data.Row;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Created by dev on 11/05/16.
 */
public class SeleniumWebTask implements SeleniumTask {
    private WebDriver driver;
    private final static Logger logger = Logger.getLogger(SeleniumWebTask.class.getName());
    private FileData fileData;
    private boolean error = false;
    private TestTask task;
    public boolean hasError() {
        return error;
    }

    public SeleniumWebTask(TestTask task) throws FileNotFoundException {
        this.task =task;
        driver = (WebDriver) task.getDriver();
    }
    protected void perform(Step step){
        WebElement element = driver.findElement(getBy(step.getId(), step.getType()));
        Row row = null != fileData?fileData.nextElement():null;
        switch(step.getAction()){
            case SELECT:{
                Select select = new Select(element);
                select.deselectAll();
                select.selectByVisibleText(getData(step,row));
                break;
            }
            case INPUT:{
                element.sendKeys(getData(step,row));
                break;
            }
            default:{
                element.click();
            }
        }
        if(step.getWait() > 0){
            driver.manage().timeouts().implicitlyWait(step.getWait(), TimeUnit.MILLISECONDS);
        }
        if(null != step.getAsserts()){
            for(String test : step.getAsserts()){
                boolean value = driver.getPageSource().contains(test);
                if(!value){
                    String errorString = "Test : "+task.getName()+" with Step name : "+step.getName()+" and Assert "+test+" failed.";
                    logger.log(Level.WARNING,errorString);
                    error = true;
                }
//                if(!value && step.getMaxErrorScreens() > step.getErrorScreenShot()){
//                    try {
////                        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
////                        ImageIO.write(image, "png", new File("/home/dev//screenshot.png"));
//                        File newFile = new File("/home/dev/"+ testName+"_"+step.getStepName()+"_"+RandomStringUtils.randomAlphabetic(6)+".PNG");
//                        if(driver instanceof HeadlessScreenshotDriver) {
//                            byte[] zipFileBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//                            FileUtils.writeByteArrayToFile(newFile, zipFileBytes);
//                            //System.out.println("File name is "+newFile.getName());
//                        }else{
//                            File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//                            //System.out.println("File name is "+newFile.getName());
//                            FileUtils.copyFile(scrFile,newFile);
//                        }
//                        step.incrementScreenShot();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                //assertTrue();
            }
        }
    }

    private String getData(Step step, Row row) {
//        int index = fileData.getIndex(step.getId());
//        //System.out.println(step.getIdentifier()+" index is "+index+" and value is "+row.getData().get(index).getData());
//        return null != row?row.getData().get(index).getData():step.getValue();
        return step.getValue();
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
            case XPATH: {
                by = By.xpath(id);
                break;
            }
            case PARTIALTEXT: {
                by = By.partialLinkText(id);
                break;
            }
            default :{
                by = By.xpath(id);
            }
        }
        return by;
    }
    public void execute(){
        logger.info("Executing test "+task.getName());
        Step stepA = null;
        try {
            for (Step step : task.getSteps()) {
                stepA = step;
                this.perform(step);
                if(step.getWait() > 0)
                    LoadRunner.pause(step.getWait()*1000);
            }
        }catch(Exception e){

            logger.info("Test "+task.getName()+" Failed.");
            logger.info("Step data : "+(null != stepA ?stepA.toJson():" Step is null"));
            logger.info("Exception is : "+e.getMessage());
            e.printStackTrace();
            assert(false);
        }
    }
}
