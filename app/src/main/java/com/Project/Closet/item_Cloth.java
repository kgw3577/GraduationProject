package com.Project.Closet;

public class item_Cloth {
    private String address;
    private String thumbnail;
    private String latitude;
    private String longitude;
    private String notice;

    public String getAddress() {
        return address;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getNotice() {return notice;}
    public void setNotice(String notice) {this.notice = notice;}
    public void setAddress(String address) {
        this.address = address;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude){
        this.longitude = longitude;
    }
}
