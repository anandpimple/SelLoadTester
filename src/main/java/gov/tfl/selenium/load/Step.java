package gov.tfl.selenium.load;

import com.google.gson.Gson;
import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 * Created by dev on 11/05/16.
 */
public class Step implements Jsonable{
    private String stepName;
    private String identifier;
    private String value;
    private Type type;
    private Action action;
    private long wait;
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getWait() {
        return wait;
    }

    public void setWait(long wait) {
        this.wait = wait;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    public String toString(){
        return gson.toJson(this);
    }
    public String getStepName() {
        return stepName;
    }
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

}
