package com.example.runmaze.stravazpot.upload.request;

import com.example.runmaze.stravazpot.upload.api.UploadAPI;
import com.example.runmaze.stravazpot.upload.model.UploadStatus;
import com.example.runmaze.stravazpot.upload.rest.UploadRest;

import retrofit2.Call;

public class CheckUploadStatusRequest {

    private final int id;
    private final UploadRest uploadRest;
    private final UploadAPI uploadAPI;

    public CheckUploadStatusRequest(int id, UploadRest uploadRest, UploadAPI uploadAPI) {
        this.id = id;
        this.uploadRest = uploadRest;
        this.uploadAPI = uploadAPI;
    }

    public UploadStatus execute() {
        Call<UploadStatus> call = uploadRest.checkUploadStatus(id);
        return uploadAPI.execute(call);
    }
}
