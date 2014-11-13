package com.androyen.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by rnguyen on 11/12/14.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = PhotoGalleryFragment.class.getSimpleName();

    GridView mGridView;
    ArrayList<GalleryItem> mItems;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true); //Retain fragment during rotation

        //Start the AsyncTask background thread
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        mGridView = (GridView)v.findViewById(R.id.gridView);
        setupAdapter(); //Call in onCreateView for any device rotation

        return v;
    }

    //GridAdapter
    void setupAdapter() {
        if (getActivity() == null || mGridView == null) {
            return;
        }

        if (mItems != null) {
            //If there are items. set the ArrayAdapter
            mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(), android.R.layout.simple_gallery_item, mItems));
        }
        else {
            //Set adapter to null
            mGridView.setAdapter(null);
        }
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {

        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            //Call the networking methods on this separate thread
//            try {
//                String result = new FlickrFetchr().getUrl("http://www.google.com");
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            }
//            catch (IOException e) {
//                Log.e(TAG, "Failed to fetch URL: " + e);
//            }
            return new FlickrFetchr().fetchItems();


        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> items) {
            //This method updates the main UI thread with list of Flickr captions
            mItems = items;
            setupAdapter();
        }


    }
}
