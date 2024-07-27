package com.amadeus.studysync.upload;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final UploadRepository repository;

    @Override
    public List<Upload> findAll() {
        return repository.findAll();
    }

    @Override
    public Upload findById(UUID theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Upload save(PostUploadRequest theUpload) {
        var file = Upload.builder()
                .id(theUpload.getId())
                .title(theUpload.getTitle() != null
                        ? theUpload.getTitle()
                        : theUpload.getName().split("\\.")[0])
                .name(theUpload.getName())
                .type(theUpload.getType())
                .isIndexed(false)
                .build();

        return repository.save(file);
    }


    @Override
    public void deleteById(UUID theId) {
        repository.deleteById(theId);
    }

    @Override
    public Upload update(Upload theUpload) {
        repository.findById(theUpload.getId())
                .orElseThrow(() -> new NotFoundException("Upload not found with id - " + theUpload.getId()));
        return repository.save(theUpload);
    }

    @Override
    public Upload partialUpdate(UUID theId, PatchUploadRequest updates) {
        Upload upload = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Upload not found with email - " + theId));

        upload.setTitle(updates.getTitle() != null ? updates.getTitle() : upload.getTitle());
        upload.setName(updates.getName() != null ? updates.getName() : upload.getName());
        upload.setType(updates.getType() != null ? updates.getType() : upload.getType());
        upload.setIsIndexed(updates.getIsIndexed() != null ? updates.getIsIndexed() : upload.getIsIndexed());

        return repository.save(upload);
    }

    @Override
    public Upload findByName(String theName) {
        return repository.findByName(theName);
    }
}