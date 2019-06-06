package com.epf.museo;

public class Data {
    private int imageID;
    private String description;

    public Data() {
    }

    public Data(int imageID, String description) {
        this.imageID = imageID;
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
