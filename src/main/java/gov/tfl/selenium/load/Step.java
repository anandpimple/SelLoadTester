package gov.tfl.selenium.load;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.List;

/**
 * Created by dev on 11/05/16.
 */
public class Step implements Jsonable<Step>{
    private String name;
    private String id;
    private String value;
    private String type;
    private String action;
    @SerializedName("mappeddata")
    private String datamapping;
    private List<String> asserts;
    private long wait = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return null != type ? Type.valueOf(type.toUpperCase()):Type.ID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Action getAction() {
        return null != action ? Action.valueOf(action.toUpperCase()):Action.CLICK;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDatamapping() {
        return datamapping;
    }

    public void setDatamapping(String datamapping) {
        this.datamapping = datamapping;
    }

    public List<String> getAsserts() {
        return asserts;
    }

    public void setAsserts(List<String> asserts) {
        this.asserts = asserts;
    }

    public long getWait() {
        return wait;
    }

    public void setWait(long wait) {
        this.wait = wait;
    }
    public String toString(){
        return super.toString()+" : "+toJson();
    }
}
