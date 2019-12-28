package com.coecs.mobilebasedtouristspotandshopnavigator;

public class ImageInformation {

    private String uid; //Uploader
    private long datePublished;

    private String photoLink;

    public ImageInformation() {
    }

    public ImageInformation(String uid, long datePublished, String photoLink) {
        this.uid = uid;
        this.datePublished = datePublished;
        this.photoLink = photoLink;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(long datePublished) {
        this.datePublished = datePublished;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }
}
