package gov.tfl.selenium.data;

import java.util.*;

/**
 * Created by dev on 14/06/16.
 */
public class Row extends DataEnumerator<Column> {
    private final int rowNo;
    private int index = 0;
    public Row(final List<Header> headers, final String rowData, final int rowNo){
        this.rowNo = rowNo;
        if(null != headers) {
            String datas[] = null != rowData ? rowData.split(",") : null;
            for (Header header : headers) {
                data.add(new Column(header, rowNo, getData(datas, header.getIndex())));
            }
        }
    }
    private String getData(String datas [], int index){
        try{
            return datas[index];
        }catch(Throwable e){
            return null;
        }
    }
}
