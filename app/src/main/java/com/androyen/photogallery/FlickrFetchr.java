package com.androyen.photogallery;

import android.net.Uri;
import android.util.Log;

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

    public static final String TAG = "FlickrFetchr";

    private static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    private static final String API_KEY = "93f326b4268077daea2d034d51be6434";
    private static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    private static final String PARAMS_EXTRAS = "extras";
    private static final String EXTRA_SMALL_URL = "url_s";

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

    //Fetch from Flickr
    public void fetchItems() {
        try {
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_GET_RECENT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter(PARAMS_EXTRAS, EXTRA_SMALL_URL)
                    .build().toString();

            String xmlString = getUrl(url);
            Log.i(TAG, "Received xml: " + xmlString);
        }
        catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
    }
}
