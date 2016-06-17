package gov.tfl.selenium.data;

/**
 * Created by dev on 14/06/16.
 */
public class Column {
    private Header headerInfo;
    private int rowId;
    private String data;

    public Column(final Header headerInfo, final int rowId, final String data) {
        this.headerInfo = headerInfo;
        this.rowId = rowId;
        this.data = data;
    }

    public Header getHeaderInfo() {
        return headerInfo;
    }

    public int getRowId() {
        return rowId;
    }

    public String getData() {
        return data;
    }
}
