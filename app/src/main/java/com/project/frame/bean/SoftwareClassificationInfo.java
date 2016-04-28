package com.project.frame.bean;

/**
 * Created by lizhe on 2015/7/13.
 */
public class SoftwareClassificationInfo {

    private int id;
    private String name;

    public SoftwareClassificationInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
