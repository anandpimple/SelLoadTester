package gov.tfl.selenium.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dev on 14/06/16.
 */
public class FileData implements Enumeration<Row> {
    public static final String SEPERATOR = ",";
    private List<Header> headersLst = new ArrayList<Header>();
    private InputStream stream;
    private Scanner scanner;
    private int rowNum = 0;
    private Row currentRow;
    public FileData(String fileName) throws FileNotFoundException {
        scanner = new Scanner(stream = new FileInputStream(fileName));
        if(scanner.hasNext()){
            addHeaders(scanner.next());
//            System.out.println("Headers are : "+headersLst);
        }
    }
    public void finalize() throws Exception{
        if(null != stream){
            stream.close();
        }
        if (null == scanner){
            scanner.close();
        }
    }
    private void addHeaders(String headers){
//        System.out.println("Header is : "+headers);
        if(null != headers){
            String headerArray [] = headers.split(SEPERATOR);
            int index = 0;
            for(String name : headerArray){
                if(name != null && !name.isEmpty())
                    headersLst.add(new Header(name, index++));
            }

        }
    }

    @Override
    public boolean hasMoreElements() {
        return scanner.hasNext();
    }

    @Override
    public Row nextElement() {
        return hasMoreElements()?currentRow=new Row(headersLst,scanner.next(),rowNum++):currentRow;
    }

    public int getIndex(String headerName){
//        System.out.println(headersLst+" Contains "+headersLst.contains(new Header(headerName,-1)));
        return headersLst.indexOf(new Header(headerName,-1));
    }
}
