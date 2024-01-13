package com.hackstudio.passengerguide.LostAndFound;

public class LostItem {
    private String date;
    private String imageUrl;
    private String stationName;

    public LostItem(String date, String imageUrl, String stationName) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.stationName = stationName;
    }

    public String getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStationName() {
        return stationName;
    }
}
