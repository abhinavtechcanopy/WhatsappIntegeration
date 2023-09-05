package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.CustomUser;
import org.example.service.CustomUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final CustomUserService customUserService;

    @PostMapping
    public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser user) {
        return new ResponseEntity<>(customUserService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomUser> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(customUserService.getUserById(userId), HttpStatus.FOUND);
    }

    @GetMapping("/list")
    public List<CustomUser> getAllUsers() {
        return customUserService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomUser> updateUser(@PathVariable String userId, @RequestBody CustomUser updatedUser) {
        return new ResponseEntity<>(customUserService.updateUser(userId, updatedUser), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return new ResponseEntity<>(customUserService.deleteUser(userId), HttpStatus.OK);
    }

}
