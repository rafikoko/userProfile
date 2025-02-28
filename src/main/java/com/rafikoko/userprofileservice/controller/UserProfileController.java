package com.rafikoko.userprofileservice.controller;

import com.rafikoko.userprofileservice.model.UserProfile;
import com.rafikoko.userprofileservice.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@CrossOrigin(origins = "http://localhost:3000")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // Create a new user profile
    @PostMapping
    public ResponseEntity<UserProfile> createProfile(@Valid @RequestBody UserProfile profile) {
        UserProfile createdProfile = userProfileService.createUserProfile(profile);
        return ResponseEntity.ok(createdProfile);
    }

    // Retrieve all profiles
    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllProfiles() {
        List<UserProfile> profiles = userProfileService.getAllProfiles();
        return ResponseEntity.ok(profiles);
    }

    // Retrieve a specific profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfileById(@PathVariable("id") Long id) {
        UserProfile profile = userProfileService.getProfileById(id);
        return ResponseEntity.ok(profile);
    }

    // Update an existing profile
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> updateProfile(@PathVariable("id") Long id, @Valid @RequestBody UserProfile profile) {
        UserProfile updatedProfile = userProfileService.updateUserProfile(id, profile);
        return ResponseEntity.ok(updatedProfile);
    }

    // Delete a profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("id") Long id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
