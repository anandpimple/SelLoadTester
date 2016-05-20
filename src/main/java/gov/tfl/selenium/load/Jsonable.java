package gov.tfl.selenium.load;

import com.google.gson.Gson;

/**
 * Created by dev on 12/05/16.
 */
public interface Jsonable<T> {
    Gson gson = new Gson();
    default T toObject(String json){
        return null != json?(T)gson.fromJson(json,this.getClass()):null;
    }
    default String toJson(){
        return gson.toJson(this);
    }
}
