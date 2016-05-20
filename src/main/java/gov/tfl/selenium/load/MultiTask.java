package gov.tfl.selenium.load;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by User on 4/11/2016.
 */
public class MultiTask {
    List<Callable> steps = new ArrayList<Callable>();
    private Callable initTask, endTask;
    private Config config;
    public MultiTask (Callable initTask, Callable endTask,List<Callable> steps ){
        this.endTask = endTask;
        this.initTask = initTask;
        this.steps = steps;
    }
    public void setConfig(Config config){
        this.config = config;
    }

    public String call() throws Exception {
        if(null != initTask){
            initTask.call();
        }
        if(!steps.isEmpty()) {
            while (!config.shouldTerminate()) {
                for (Callable task : steps){
                    task.call();
                }
            }
        }
        if(null != endTask){
            endTask.call();
        }
        return "Success";
    }

}
