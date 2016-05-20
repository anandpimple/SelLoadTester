package gov.tfl.selenium.load;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by User on 4/11/2016.
 */
public class Config {
    public static final String LOADCONFIG_PROPS = "loadconfig.props";
    public static final String MAX_LOAD = "maxLoad";
    public static final String RUN_TIME = "runTime";
    public static final String TERMINATE_NOW = "terminateNow";
    public static final String BASE_URL = "baseUrl";
    public static final String PAGE_WAIT = "pageWait";
    private boolean background = false;
    private Properties properties = new Properties();
    private long startTime = System.currentTimeMillis();
    private long lastModified;
    public Config() throws IOException{
        loadProps();
    }

    private long getLastModified(){
        File file = new File(getClass().getClassLoader().getResource(LOADCONFIG_PROPS).getFile());
        return file.lastModified();
    }
    private void loadProps() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(LOADCONFIG_PROPS);
        try {
            properties.load(stream);
            lastModified = getLastModified();
        }finally {
            stream.close();
        }
    }

    public String getBaseUrl(){
        return getConfig(BASE_URL);
    }
    public long getPageWait(){
        try{
            return Long.parseLong(getConfig(PAGE_WAIT));
        }catch(Exception e){
            return 1000l;
        }
    }
    public int getMaxLoad(){
        try {
            return Integer.parseInt(properties.getProperty(MAX_LOAD));
        }catch (Exception e){
            return 1;
        }
    }

    private boolean terminateNow() throws IOException{
        try {
            if(lastModified < getLastModified()){
                loadProps();
            }
            return Boolean.valueOf(properties.getProperty(TERMINATE_NOW));
        }catch(Exception e){
            return false;
        }
    }

    private long getRunTime(){
        try {
            return Long.parseLong(properties.getProperty(RUN_TIME))*1000;
        }catch (Exception e){
            return 10*1000;
        }
    }
    public void resetStartTime(){
        startTime = System.currentTimeMillis();
    }
    public boolean shouldTerminate() throws IOException{
        return terminateNow() || getRunTime() <= (System.currentTimeMillis() - startTime);
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public String getConfig(String name){
        return properties.getProperty(name);
    }
}
