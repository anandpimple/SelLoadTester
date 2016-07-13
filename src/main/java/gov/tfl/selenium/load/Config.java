package gov.tfl.selenium.load;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 4/11/2016.
 */

public class Config {
    @SerializedName("visible")
    private boolean visibile = true;
    @SerializedName("baseUrl")
    private String baseUrl;
    @SerializedName("load")
    private long noOfTotalThreads = -1;
    @SerializedName("time")
    private long runTime = -1;
    @SerializedName("timeLaps")
    private long timeLaps = -1;
    @SerializedName("rampUpTime")
    private long rampUpTime = -1;
    @SerializedName("pageWait")
    private long defaultPageWait = -1;

    public boolean isVisibile() {
        return visibile;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getNoOfTotalThreads() {
        return noOfTotalThreads;
    }

    public void setNoOfTotalThreads(long noOfTotalThreads) {
        this.noOfTotalThreads = noOfTotalThreads;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public long getTimeLaps() {
        return timeLaps;
    }

    public void setTimeLaps(long timeLaps) {
        this.timeLaps = timeLaps;
    }

    public long getRampUpTime() {
        return rampUpTime;
    }

    public void setRampUpTime(long rampUpTime) {
        this.rampUpTime = rampUpTime;
    }

    public long getDefaultPageWait() {
        return defaultPageWait;
    }

    public void setDefaultPageWait(long defaultPageWait) {
        this.defaultPageWait = defaultPageWait;
    }
}
