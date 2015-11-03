package com.demo.mummyding.multiimagechooser.model;

/**
 * Created by mummyding on 15-11-3.
 */
public class ImageBean {
    private String imageUri;
    private boolean isChecked;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
