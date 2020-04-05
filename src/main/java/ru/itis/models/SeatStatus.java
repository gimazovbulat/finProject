package ru.itis.models;

public enum SeatStatus {
    FREE("FREE"), BOOKED("BOOKED"), BOUGHT("BOUGHT");

    String state;

    SeatStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
