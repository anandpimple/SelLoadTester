package gov.tfl.selenium.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 14/06/16.
 */
public class DataEnumerator<T> implements DataEnumeration<T> {
    protected List<T> data = new ArrayList<T>();
    private int index = 0;
    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public int getCurrentIndex() {
        return index;
    }

    @Override
    public void increaseIndex() {
        ++index;
    }
}
