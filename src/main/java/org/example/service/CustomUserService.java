package org.example.service;

import org.example.entity.CustomUser;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CustomUserService {
    private final CustomUserRepository userRepository;

    @Autowired
    public CustomUserService(CustomUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CustomUser createUser(CustomUser user) {
        return userRepository.save(user);
    }

    public CustomUser getUserById(String userId) {
        Optional<CustomUser> optionalCustomUser = userRepository.findById(userId);
        if (optionalCustomUser.isPresent())
            return optionalCustomUser.get();
        throw new ResourceNotFoundException("User not Found");
    }

    public List<CustomUser> getAllUsers() {

        return userRepository.findAll();
    }

    public CustomUser updateUser(String userId, CustomUser updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }

    public String deleteUser(String userId) {

        userRepository.deleteById(userId);
        return "user Deleted Successfully";

    }
}