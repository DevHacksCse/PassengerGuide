package com.hackstudio.passengerguide.Models;

public class ParkingSpace {
    private String userId;
    private String location;
    private int capacity;
    private String type;
    private double price;

    // Default constructor required for Firestore
    public ParkingSpace() {
    }

    // Parameterized constructor
    public ParkingSpace(String userId, String location, int capacity, String type, double price) {
        this.userId = userId;
        this.location = location;
        this.capacity = capacity;
        this.type = type;
        this.price = price;
    }

    // Getter and Setter methods for all fields

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString() method for debugging or logging purposes
    @Override
    public String toString() {
        return "ParkingSpace{" +
                "userId='" + userId + '\'' +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
