package com.epf.museo.models;

import java.io.File;

public class MuseePhoto {

    private File filePhoto;

    public MuseePhoto(File filePhoto) {
        this.filePhoto = filePhoto;
    }

    public File getFilePhoto() {
        return filePhoto;
    }

    public void setFilePhoto(File filePhoto) {
        this.filePhoto = filePhoto;
    }
}
