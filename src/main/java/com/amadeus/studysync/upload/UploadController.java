package com.amadeus.studysync.upload;

import com.amadeus.studysync.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadServiceImpl service;

    @GetMapping
    public ResponseEntity<List<UploadResponse>> findAllUploads() {
        List<Upload> uploads = (service.findAll());

        return ResponseEntity.ok(UploadResponse.from(uploads));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Upload> findUploadById(@PathVariable UUID id) throws Exception {
        Upload file = service.findById(id);

        if (file == null) {
            throw new NotFoundException("Upload not found - " + id);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Upload> findUploadByName(@PathVariable String name) throws Exception {
        Upload file = service.findByName(name);

        if (file == null) {
            throw new NotFoundException("Upload not found - " + name);
        }

        return ResponseEntity.ok(file);
    }

    @PostMapping
    public ResponseEntity<UploadResponse> save(
            @RequestBody UploadRequest request
    ) {
        Upload response = service.save(request);

        return new ResponseEntity<>(UploadResponse.from(response), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Upload> update(
            @RequestBody Upload request
    ) {
        return ResponseEntity.ok((service.update(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Upload> partiallyUpdate(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Upload updatedUpload = service.partialUpdate(id, updates);
        return ResponseEntity.ok(updatedUpload);
    }
}
