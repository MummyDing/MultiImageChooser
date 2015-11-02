package com.demo.mummyding.photowall;

import android.net.Uri;

/**
 * Created by mummyding on 15-11-2.
 */
public class ImageBean {
    private Uri imgUrl;
    private Uri thumbnailUrl;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public ImageBean setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
        return this;
    }

    public Uri getImgUrl() {
        return imgUrl;
    }

    public ImageBean setImgUrl(Uri imgUrl) {
        this.imgUrl = imgUrl;
        return  this;
    }

    public Uri getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ImageBean setThumbnailUrl(Uri thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
        return this;
    }
}
