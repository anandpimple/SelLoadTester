package gov.tfl.selenium.load;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;

import javax.naming.OperationNotSupportedException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 4/11/2016.
 */
public class LoadRunner {
    private final static Logger logger = Logger.getLogger(LoadRunner.class.getName());
    private ExecutorService executor;
    private Config config;
    private List<SeleniumWebJob> seleniumWebJobs = new ArrayList<SeleniumWebJob>();
    private String dataDirectory = System.getenv("HOME")+"/seltestdata/";
    public LoadRunner(Config config) throws Exception {
        this.config = config;
        Gson gson = new Gson();
        String myData = getDataAsString(config.getConfig("data"));
        logger.log(Level.FINE,"Json Data \n"+myData);
        Map map = gson.fromJson(myData,Map.class);
        for(Object obj : map.entrySet()){
            for(int i = 0 ; i < config.getMaxLoad(); i ++){
                createSeleniumJob((Map.Entry)obj);
            }
            break;
        }

        executor =  Executors.newFixedThreadPool(config.getMaxLoad());
    }
    private void createSeleniumJob(Map.Entry entry) throws Exception {
        logger.log(Level.INFO,"Creating Job : "+entry.getKey());
        SeleniumWebJob job = new SeleniumWebJob(config);
        try {
            configureJob(job, (Map) entry.getValue());
            seleniumWebJobs.add(job);
        }catch(Throwable t){
            job.destroy();
            throw t;
        }

    }

    private void configureJob(SeleniumWebJob job, Map value) {
        logger.log(Level.INFO,"Value is : "+value);
        job.setUrl((String)value.get("url"));
        job.setStartTask(configureTask((Map)value.get("startTask"),job.getDriver()));
        job.setEndTask(configureTask((Map)value.get("endTask"),job.getDriver()));
        Map repetableTask = (Map)value.get("repetableTasks");
        if(null != repetableTask) {
            List<SeleniumTask> repetableSelTask = new ArrayList<SeleniumTask>();
            for (Object obj : repetableTask.entrySet()) {
                Map.Entry stepEntry = (Map.Entry)obj;
                logger.log(Level.INFO,"Configuring repetable task "+(String)stepEntry.getKey());
                repetableSelTask.add(configureTask((Map)stepEntry.getValue(),job.getDriver()));
            }
            job.setRepetableTask(repetableSelTask);
        }
    }
    private SeleniumTask configureTask(Map task, WebDriver driver){
        SeleniumTask selTask = null;
        if(null != task){
            String type = (String)task.get("type");
            String inputFilePath = null != (String)task.get("inputFile")?dataDirectory+(String)task.get("inputFile"):null;
            if(null == type || type.equalsIgnoreCase("WEBTASK"))

                try {
                    selTask = new SeleniumWebTask(driver,(String)task.get("name"),configureSteps(task),(String)task.get("url"),inputFilePath);
                } catch (FileNotFoundException e) {
                    throw new IllegalArgumentException("Input file "+inputFilePath+" have issue.",e);
                }
            else
                throw new IllegalArgumentException("Only Web tasks are supported");

        }
        return selTask;
    }
    private List<Step> configureSteps(Map task){
        List<Step> steps = new ArrayList<Step>();
        Map stepJson = (Map)task.get("steps");
        for(Object obj : stepJson.entrySet()){
            Map.Entry stepEntry = (Map.Entry)obj;
            Step step = new Step();
            step.setStepName((String)stepEntry.getKey());
            Map stepData = (Map)stepEntry.getValue();
            step.setIdentifier((String)stepData.get("identifier"));
            try {
                step.setWait((Long) stepData.get("wait"));
            }catch(Exception nf){
                logger.log(Level.INFO,"Exception while converting stpe wait for step "+step.getStepName()+" Exception is : "+nf.getMessage());
            }
            step.setValue((String)stepData.get("value"));
            String action = (String)stepData.get("action");
            String type = (String)stepData.get("type");
            if(null != stepData.get("assertData"))
                step.setAssertData((List)stepData.get("assertData"));
            step.setAction(null != action?Action.valueOf(action.toUpperCase()):Action.CLICK);
            step.setType(null != type?Type.valueOf(type.toUpperCase()):Type.ID);
            logger.log(Level.INFO,step.toString());
            steps.add(step);
        }
        return steps;
    }
    public static String getDataAsString(String fileName) throws IOException {
        InputStream stream = null;
        Scanner scanner = null;
        try {
            stream = LoadRunner.class.getClassLoader().getResourceAsStream(fileName);
            scanner = new Scanner(stream, "UTF-8");
            scanner.useDelimiter("\\A");
            return scanner.hasNext()?scanner.next():"";
        }finally {
            if (null != stream)
                stream.close();
            if(null != scanner)
                scanner.close();
        }

    }

    public void processLoad() throws InterruptedException, IOException {
        try{
           executor.invokeAll(seleniumWebJobs);
        }finally{
            executor.shutdownNow();
        }
    }
    public static void main(String [] args){
        try {
            new LoadRunner(new Config()).processLoad();
//            System.out.println(new Date(1463388702845l));
//            System.out.println(new Date(1463208682357l));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
