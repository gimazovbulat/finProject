package ru.itis.models;

public enum RoomStatus {
    FREE("FREE"), BOOKED("BOOKED"), BOUGHT("BOUGHT");

    String state;

    RoomStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
