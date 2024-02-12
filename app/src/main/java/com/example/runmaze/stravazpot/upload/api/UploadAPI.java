package com.example.runmaze.stravazpot.upload.api;

import com.example.runmaze.stravazpot.common.api.Config;
import com.example.runmaze.stravazpot.common.api.StravaAPI;
import com.example.runmaze.stravazpot.upload.request.CheckUploadStatusRequest;
import com.example.runmaze.stravazpot.upload.request.UploadFileRequest;
import com.example.runmaze.stravazpot.upload.rest.UploadRest;

import java.io.File;

public class UploadAPI extends StravaAPI {

    public UploadAPI(Config config) {
        super(config);
    }

    public UploadFileRequest uploadFile(File file) {
        return new UploadFileRequest(file, getAPI(UploadRest.class), this);
    }

    public CheckUploadStatusRequest checkUploadStatus(int id) {
        return new CheckUploadStatusRequest(id, getAPI(UploadRest.class), this);
    }
}
