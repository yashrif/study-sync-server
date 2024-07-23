package com.amadeus.studysync.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<UserResponse> findUser(Principal connectedUser) {
        return ResponseEntity.ok(UserResponse.from(service.getUser(connectedUser)));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> partialUpdate(@RequestBody PatchUserRequest updates, Principal connectedUser) {
        User updatedUser = service.partialUpdate(updates, connectedUser);
        return ResponseEntity.ok(UserResponse.from((updatedUser)));
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
