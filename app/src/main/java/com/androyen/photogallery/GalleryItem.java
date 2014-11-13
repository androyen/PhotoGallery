package com.androyen.photogallery;

/**
 * Created by rnguyen on 11/12/14.
 */

//Model class for the Flickr XML data
public class GalleryItem {

    private String mCaption;
    private String mId;
    private String mUrl;

    public String toString() {
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
