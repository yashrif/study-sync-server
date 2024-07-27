package com.amadeus.studysync.upload;

import com.amadeus.studysync.service.AppService;

import java.util.UUID;

public interface UploadService extends AppService<Upload, PostUploadRequest, PatchUploadRequest, UUID> {
    Upload findByName(String name);
}
