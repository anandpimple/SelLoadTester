package gov.tfl.selenium.load;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by User on 4/11/2016.
 */
public class LoadRunner {

    private ExecutorService executor;
    private Config config;
    public LoadRunner(Config config) throws IOException {
        this.config = config;
        executor =  Executors.newFixedThreadPool(config.getMaxLoad());
    }

    public void processLoad() throws InterruptedException {
        List<SeleniumWebJob> list = new ArrayList<SeleniumWebJob>();
        for(int i = 0 ; i < config.getMaxLoad(); i ++){
            list.add(new SeleniumWebJob(config));
        }
        try{
           executor.invokeAll(list);
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
