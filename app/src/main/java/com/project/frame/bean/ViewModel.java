package com.project.frame.bean;

/**
 * Created by lizhe on 2015/7/28.
 */
public class ViewModel {

    private String image;
    private String title;

    public ViewModel(String title, String image) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
