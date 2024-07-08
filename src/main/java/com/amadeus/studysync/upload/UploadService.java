package com.amadeus.studysync.upload;

import com.amadeus.studysync.service.AppService;

public interface UploadService extends AppService<Upload, UploadRequest, String> {
    Upload findByName(String name);
}
