package com.amadeus.studysync.upload;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final UploadServiceImpl service;

    @GetMapping
    public ResponseEntity<List<UploadResponse>> findAllUploads(Principal connectedUser) {
        List<Upload> uploads = (service.findAll(connectedUser));

        return ResponseEntity.ok(UploadResponse.from(uploads));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Upload> findUploadById(@PathVariable UUID id, Principal connectedUser) throws Exception {
        Upload file = service.findById(id, connectedUser);

        if (file == null) {
            throw new NotFoundException("Upload not found - " + id);
        }

        return ResponseEntity.ok(file);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<Upload> findUploadByName(@PathVariable String name, Principal connectedUser) throws Exception {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Upload file = service.findByName(name, connectedUser);

        if (file == null) {
            throw new NotFoundException("Upload not found - " + name);
        }

        return ResponseEntity.ok(file);
    }

    @PostMapping
    public ResponseEntity<UploadResponse> save(
            @RequestBody PostUploadRequest request
    ) {
        Upload response = service.save(request);

        return new ResponseEntity<>(UploadResponse.from(response), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Upload> update(
            @RequestBody Upload request, Principal connectedUser
    ) {
        return ResponseEntity.ok((service.update(request, connectedUser)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Upload> partiallyUpdate(@PathVariable UUID id, @RequestBody PatchUploadRequest updates, Principal connectedUser) {
        Upload updatedUpload = service.partialUpdate(id, updates, connectedUser);
        return ResponseEntity.ok(updatedUpload);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id, Principal connectedUser) {
        service.deleteById(id, connectedUser);
    }
}
