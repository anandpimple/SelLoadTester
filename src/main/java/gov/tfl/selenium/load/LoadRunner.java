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
        executor =  Executors.newFixedThreadPool((int)config.getNoOfTotalThreads());
        long waitForNextThread = (config.getRampUpTime()/10)*1000;
        for( int i = 1; i <= config.getNoOfTotalThreads(); i++){
            if(i > 1){
                pause(waitForNextThread);
            }
            seleniumWebJobs.add(new SeleniumJobInvoker(config,test));
        }
    }
    public static void pause(long time){
        long ctime = System.currentTimeMillis();
        while(time > System.currentTimeMillis()-ctime){
            logger.log(Level.FINEST,"Paused....");
        }
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
            logger.info("Started processing threads");
            executor.invokeAll(seleniumWebJobs);
        }finally{
            executor.shutdownNow();
        }
    }
}
