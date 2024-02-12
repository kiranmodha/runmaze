package com.example.runmaze.stravazpot.athlete.model;

public enum FriendStatus {
    PENDING("pending"), ACCEPTED("accepted"), BLOCKED("blocked"), NOT_FRIENDS("null");

    private String rawValue;

    FriendStatus(String rawValue) {
        this.rawValue = rawValue;
    }

    @Override
    public String toString() {
        return rawValue;
    }
}
