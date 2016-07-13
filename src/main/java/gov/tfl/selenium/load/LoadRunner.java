package gov.tfl.selenium.load;

import com.google.gson.Gson;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 4/11/2016.
 */
public class LoadRunner {
    private final static Logger logger = Logger.getLogger(LoadRunner.class.getName());
    private ExecutorService executor;
    private Config config;
    private Test test;
    private List<SeleniumJobInvoker> seleniumWebJobs = new ArrayList<SeleniumJobInvoker>();
    private Gson gson = new Gson();
    private long testStartTime;
    public LoadRunner(String filePath) throws Exception {
        //String fileLocation = System.getProperties().getProperty("dataFileLocation");
        String myData = getDataAsString(filePath);
        logger.log(Level.FINEST,myData);
        configure( gson.fromJson(myData,Map.class));

        testStartTime = System.currentTimeMillis();
        logger.info("Started test at : "+new Date(testStartTime));
        for( int i = 1; i <= config.getNoOfTotalThreads(); i++){
            executor.submit(new SeleniumJobInvoker(config,test));
        }
        executor =  Executors.newFixedThreadPool((int)config.getNoOfTotalThreads());

    }

    private void configure(Map map) {
        config = gson.fromJson(gson.toJson(map.get("config")),Config.class);
        test =  gson.fromJson(gson.toJson(map.get("test")),Test.class);
    }


    public Config getConfig() {
        return config;
    }

    public Test getTest() {
        return test;
    }

    private void configureJob(SeleniumJobInvoker job, Map value) {
        logger.log(Level.INFO,"Value is : "+value);
        job.setUrl((String)value.get("url"));
        //ob.setStartTask(configureTask((Map)value.get("startTask"),job.getDriver()));
       // job.setEndTask(configureTask((Map)value.get("endTask"),job.getDriver()));
        Map repetableTask = (Map)value.get("repetableTasks");
        if(null != repetableTask) {
            List<SeleniumTask> repetableSelTask = new ArrayList<SeleniumTask>();
            for (Object obj : repetableTask.entrySet()) {
                Map.Entry stepEntry = (Map.Entry)obj;
                logger.log(Level.INFO,"Configuring repetable task "+(String)stepEntry.getKey());
               // repetableSelTask.add(configureTask((Map)stepEntry.getValue(),job.getDriver()));
            }
            job.setRepetableTask(repetableSelTask);
        }
    }
    private SeleniumTask configureTask(TestTask task, WebDriver driver){
        SeleniumTask selTask = null;
//        if(null != task){
//            String inputFilePath = null;// != (String)task.get("inputFile")?dataDirectory+(String)task.get("inputFile"):null;
//            if(task.getType() == TestTask.TaskType.WebTask)
////                try {
////                } catch (FileNotFoundException e) {
////                    throw new IllegalArgumentException("Input file "+inputFilePath+" have issue.",e);
////                }
//            else
//                throw new IllegalArgumentException("Only Web tasks are supported");
//
//        }
        return selTask;
    }

    public static String getDataAsString(String fileName) throws IOException {
        InputStream stream = null;
        Scanner scanner = null;
        try {
            stream = new FileInputStream(fileName);
            scanner = new Scanner(stream, "UTF-8");
            scanner.useDelimiter("\\A");
            return scanner.hasNext()?scanner.next():"";
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        finally {

            if(null != scanner)
                scanner.close();
            if (null != stream)
                stream.close();
        }

    }

    public void processLoad() throws InterruptedException, IOException {
        try{
           executor.invokeAll(seleniumWebJobs);
        }finally{
            executor.shutdownNow();
        }
    }
}
