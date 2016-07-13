package gov.tfl.selenium.load;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dev on 12/07/16.
 */
public class TestTask {
    private String name;
    private String type;
    private String inputFile;
    private List<Step> steps;
    private Object driver;
    public enum TaskType{
        WebTask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskType getType() {
        return null != type?TaskType.valueOf(type):TaskType.WebTask;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Object getDriver() {
        return driver;
    }

    public void setDriver(Object driver) {
        this.driver = driver;
    }
}
