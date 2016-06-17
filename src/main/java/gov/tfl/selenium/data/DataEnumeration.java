package gov.tfl.selenium.data;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by dev on 14/06/16.
 */
public interface DataEnumeration<T> extends Enumeration<T> {
    List<T> getData();
    int getCurrentIndex();
    void increaseIndex();
    @Override
    default boolean hasMoreElements() {
        return getData() != null && getData().size() > getCurrentIndex();
    }
    @Override
    default T nextElement() {
        T t = null;
        if(hasMoreElements()){
            t = getData().get(getCurrentIndex());
            increaseIndex();
        }
        return t;
    }
    default int getSize(){
        return getData().size();
    }
}
