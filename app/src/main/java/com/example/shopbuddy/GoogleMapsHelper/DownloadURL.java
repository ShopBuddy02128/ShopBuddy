package com.example.shopbuddy.GoogleMapsHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadURL {

    public String readUrl(String myUrl) throws  IOException{
        String data ="";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            //Read data from url
            inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            //Read each line of input
            String line = "";
            while((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }

            //Convert result
            data = stringBuffer.toString();

            //Close the reader
            reader.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Must be run even if an exception is caught
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
