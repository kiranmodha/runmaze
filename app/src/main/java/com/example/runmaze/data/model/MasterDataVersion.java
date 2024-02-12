package com.example.runmaze.data.model;

public class MasterDataVersion {
    String master_data;
    float version_number;

    public String getMasterData() {
        return master_data;
    }

    public void setMasterData(String masterData) {
        this.master_data = masterData;
    }

    public float getVersionNumber() {
        return version_number;
    }

    public void setVersion_number(float versionNumber) {
        this.version_number = versionNumber;
    }

    public MasterDataVersion(String masterData, float versionNumber) {
        this.master_data = masterData;
        this.version_number = versionNumber;
    }

    public MasterDataVersion() {
        this.master_data = "";
        this.version_number = 0f;
    }
}
