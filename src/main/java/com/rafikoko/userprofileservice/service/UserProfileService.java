package com.rafikoko.userprofileservice.service;

import com.rafikoko.userprofileservice.exception.ResourceNotFoundException;
import com.rafikoko.userprofileservice.model.UserProfile;
import com.rafikoko.userprofileservice.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile createUserProfile(UserProfile profile) {
        // Check if the email is already registered
        if (userProfileRepository.findByEmail(profile.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + profile.getEmail());
        }
        return userProfileRepository.save(profile);
    }

    public List<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfile getProfileById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found for id: " + id));
    }

    public UserProfile updateUserProfile(Long id, UserProfile updatedProfile) {
        UserProfile existingProfile = getProfileById(id);
        existingProfile.setFirstName(updatedProfile.getFirstName());
        existingProfile.setLastName(updatedProfile.getLastName());
        existingProfile.setEmail(updatedProfile.getEmail());
        existingProfile.setPhone(updatedProfile.getPhone());
        return userProfileRepository.save(existingProfile);
    }

    public void deleteUserProfile(Long id) {
        UserProfile existingProfile = getProfileById(id);
        userProfileRepository.delete(existingProfile);
    }
}
