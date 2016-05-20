package gov.tfl.selenium.load;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by User on 4/11/2016.
 */
public class SeleniumWebJob implements Callable<String>, Jsonable {
    private WebDriver driver;
    private Config config;
    private String action;
    private SeleniumTask startTask, endTask;
    private List<SeleniumTask> repetableTask;
    private int maxRepeat = 1;
    public SeleniumWebJob(Config config){
        this.config = config;
        driver = false?new HtmlUnitDriver():new FirefoxDriver();
//        String fileName = this.config.getConfig("data");
//        Gson gson = new Gson();
//        gson.
        List<Step> loginSteps = new ArrayList<Step>();
        Step loginText = getStep("inputLogin",100,"j_username",Type.ID,"testuser7",Action.INPUT);
        Step passwordText = getStep("inputLogin",100,"j_password",Type.ID,"Password1",Action.INPUT);
        Step submit = getStep("clickLogin",100,"SignIn",Type.ID,null,Action.CLICK);
        Step logout = getStep("signOut",100,"Sign out",Type.TEXT,null,Action.CLICK);
        loginSteps.add(loginText);
        loginSteps.add(passwordText);
        loginSteps.add(submit);
        loginSteps.add(logout);
        SeleniumTask login = new SeleniumWebTask(driver,"login",loginSteps,null);
        repetableTask = new ArrayList<SeleniumTask>();
        repetableTask.add(login);
//        List<Step> logoutSteps = new ArrayList<Step>();
//        SeleniumTask logout = new SeleniumWebTask(driver,"logout",logoutSteps,null);

    }
    private Step getStep(String stapeName, long wait, String identifier, Type type, String value, Action action){
        Step step = new Step();
        step.setStepName(stapeName);
        step.setWait(wait);
        step.setIdentifier(identifier);
        step.setType(type);
        step.setAction(action);
        step.setValue(value);
        return step;
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
            driver.navigate().to(config.getBaseUrl());
            driver.manage().timeouts().implicitlyWait(config.getPageWait(), TimeUnit.MILLISECONDS);
            int taskRepetaed = 0;
            while (!config.shouldTerminate()) {
                if(maxRepeat <= 0 || maxRepeat < taskRepetaed) {
                    for (SeleniumTask task : repetableTask) {
                        task.execute();
                    }
                }
                ++taskRepetaed;
            }
        }finally{
            driver.close();
        }
        return null;
    }

}
