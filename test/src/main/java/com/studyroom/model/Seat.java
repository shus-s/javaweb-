package com.studyroom.model;

public class Seat {
    private int id;
    private int roomId;
    private String seatNumber;
    private String status;

    public Seat() {
    }

    public Seat(int roomId, String seatNumber, String status) {
        this.roomId = roomId;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}



