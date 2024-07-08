package com.amadeus.studysync.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final UploadRepository repository;

    @Override
    public List<Upload> findAll() {
        return repository.findAll();
    }

    @Override
    public Upload findById(String theId) {
        return repository.findById(theId).orElse(null);
    }

    @Override
    public Upload save(UploadRequest request) {
        var file = Upload.builder()
                .id(request.getId())
                .title(request.getTitle() != null
                        ? request.getTitle()
                        : request.getName().split("\\.")[0])
                .name(request.getName())
                .type(request.getType())
                .build();
        repository.save(file);

        return this.findById(request.getId());
    }


    @Override
    public void deleteById(String theId) {
        repository.deleteById(theId);
    }

    @Override
    public Upload findByName(String name) {
        return repository.findByName(name);
    }
}