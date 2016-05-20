import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dev on 16/05/16.
 */
public class HttpConnector {
    private static HashMap<String,ClosableConnection> connectionPool = new HashMap<String,ClosableConnection>();

    public static String postData(String url,Map<String,String> data) throws Exception{
        ClosableConnection closableConnection = connectionPool.get(url);
        if(null == closableConnection) {
            closableConnection = new ClosableConnection(url);
            connectionPool.put(url,closableConnection);
        }
        return closableConnection.getData(null);
    }
    public static void main(String [] args)  {
        try {
            System.out.println("---------------1----------------");
            System.out.println(HttpConnector.postData("http://www.google.com",null));
            System.out.println("---------------2----------------");
            System.out.println(HttpConnector.postData("http://www.google.com",null));
            System.out.println("---------------3----------------");
            System.out.println(HttpConnector.postData("http://www.google.com",null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static class ClosableConnection{
        public static final String POST = "POST";
        public static final String GET = "GET";
        public boolean inUse = true;
        private HttpURLConnection conn;
        private boolean isNew = true;

        public ClosableConnection(String url, String proxyData, int connectTimeout, int socketTimeout) throws Exception{
            conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(socketTimeout);
            conn.setRequestMethod(GET);
            conn.setDoOutput(true);
        }

        public ClosableConnection(String url) throws Exception{
            this(url,null,10000,10000);
        }
        private String postData(Map<String,String> data) throws Exception{
            inUse = true;
            try {
                //connect(POST);
                writeDataToRequest(data);
                return getResponseData();

            }finally {
                inUse = false;
                //conn.disconnect();
            }
        }
        private void connect(String method){
            try {
                //if(isNew) {
                    conn.setRequestMethod(method);
                   // conn.setDoOutput(true);
                    isNew = false;
                //}
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private String getData(Map<String,String> data) throws Exception{
            inUse = true;
            try {
                //connect(GET);
                //conn.setDoOutput(true);
                writeDataToRequest(data);
                return getResponseData();

            }finally {
                inUse = false;
                //conn.disconnect();
            }
        }
        public void finalize(){
            conn.disconnect();
            conn = null;
        }

        private String getResponseData() throws IOException {
            InputStream stream = null;
            try {
                stream = conn.getInputStream();
                Scanner scan = new Scanner(stream).useDelimiter("\\A");
                return scan.hasNext()?scan.next():"";
            }finally{
                if(null != stream)
                    stream.close();
            }
        }

        private void writeDataToRequest(Map<String,String> data) throws IOException {
            if(null != data && !data.isEmpty()){
                StringBuilder builder = new StringBuilder();
                OutputStream stream = null;
                try {
                    stream = conn.getOutputStream();
                    stream.write(builder.toString().getBytes());
                }finally{
                    if(null != stream)
                        stream.close();
                }
            }
        }

    }
}

