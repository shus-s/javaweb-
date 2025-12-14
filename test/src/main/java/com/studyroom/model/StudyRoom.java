package com.studyroom.model;

import java.sql.Timestamp;

public class StudyRoom {
    private int id;
    private String roomName;
    private String location;
    private int capacity;
    private String description;
    private String status;
    private Timestamp createdAt;

    public StudyRoom() {
    }

    public StudyRoom(String roomName, String location, int capacity, String description, String status) {
        this.roomName = roomName;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}



