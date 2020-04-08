package ru.itis.models;

public enum PaymentStatus {
    NOT_PAYED("NOT_PAYED"), PAYED("PAYED");

    String val;

    PaymentStatus(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
