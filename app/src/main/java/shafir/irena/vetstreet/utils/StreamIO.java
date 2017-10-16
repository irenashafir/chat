package shafir.irena.vetstreet.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by irena on 12/05/2017.
 */
public class StreamIO {
    public static void write(String fileName, String data) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void write(String fileName, String data, boolean append) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName, append));
            writer.write(data);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    public static String read(String fileName) throws IOException {
        StringBuilder data = new StringBuilder();
        InputStream in = new FileInputStream(fileName);
        return read(in);
    }


    public static void copy(String srcFileName, String destFileName) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(destFileName));
            in = new BufferedInputStream(new FileInputStream(srcFileName));
            byte[] buffer = new byte[8192];
            int length = 0;

            while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null & in != null) {
                out.close();
                in.close();
            }
        }
    }




    // read from the web
    public static String readWebSite(String address) throws IOException {

        URL url = null;

        try {
            url= new URL(address);
        }
        catch (MalformedURLException e){
            throw new IOException("failed at parsing the URL. " + e);
        }
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();

            // wrap the inputStream in a inputStreamReader
            // wrap the reader in a buffered reader
            // read the data
            // close everything

            return read(in);
    }


        private final static String lineSeparator = "\n";

    public static String read(InputStream inputStream) throws IOException {
        return read(inputStream, "utf-8");
    }


    public static String read(InputStream inputStream, String charset) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(lineSeparator);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return builder.toString();
    }


        public boolean  fileExists (String pathName) {
            boolean fileExists = true;
            File file = new File(pathName);
            System.out.println(file.exists());
            return fileExists;
        }




}
