package shafir.irena.vetstreet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by irena on 23/09/2017.
 */

public class DownloadURL {

    public  String readURL(String myUrl) throws IOException {
        String data ="";
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(myUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line ="";

            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            data= sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            connection.disconnect();
        }
        return data;
    }
}
