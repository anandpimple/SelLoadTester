package gov.tfl.selenium.load;

import java.util.List;

/**
 * Created by dev on 12/07/16.
 */
public class Test {
    private String name;
    private String description;
    private List<TestTask> before;
    private List<TestTask> after;
    private List<TestTask> contineous;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestTask> getBefore() {
        return before;
    }

    public void setBefore(List<TestTask> before) {
        this.before = before;
    }

    public List<TestTask> getAfter() {
        return after;
    }

    public void setAfter(List<TestTask> after) {
        this.after = after;
    }

    public List<TestTask> getContineous() {
        return contineous;
    }

    public void setContineous(List<TestTask> contineous) {
        this.contineous = contineous;
    }
}
