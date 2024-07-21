package com.amadeus.studysync.upload;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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
    public Upload save(UploadRequest theUpload) {
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
    public Upload partialUpdate(UUID theId, Map<String, Object> updates) {
        Upload upload = repository.findById(theId)
                .orElseThrow(() -> new NotFoundException("Upload not found with email - " + theId));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Upload.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, upload, value);
            }
        });

        return repository.save(upload);
    }

    @Override
    public Upload findByName(String theName) {
        return repository.findByName(theName);
    }
}