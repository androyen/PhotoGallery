package com.androyen.photogallery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by rnguyen on 11/12/14.
 */

//Utility class to handle networking
public class FlickrFetchr {

    byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec); //Create URL object to parse
        HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //Create Http connection

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream(); //Connects to endpoint

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read()) > 0) {
                out.write(buffer, 0, bytesRead); //Write bytes to buffer
            }

            out.close();
            return out.toByteArray(); //return the raw bytes
        }
        finally {
            connection.disconnect(); //Close connection
        }
    }

    public String getUrl(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec)); //Convert the raw bytes to string
    }
}
