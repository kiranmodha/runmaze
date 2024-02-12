package com.example.runmaze.stravazpot.authenticaton.api;

public enum AccessScope {
    READ("read"),
    READ_ALL("read_all"),
    PROFILE_READ_ALL("profile:read_all"),
    PROFILE_WRITE("profile:write"),
    ACTIVITY_READ("activity:read"),
    ACTIVITY_READ_ALL("activity:read_all"),
    ACTIVITY_WRITE("activity:write");

    private String rawValue;

    AccessScope(String rawValue) {
        this.rawValue = rawValue;
    }
    
    @Override
    public String toString() {
        return rawValue;
    }
}
