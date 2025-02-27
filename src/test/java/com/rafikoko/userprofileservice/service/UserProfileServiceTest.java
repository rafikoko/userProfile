package com.rafikoko.userprofileservice.service;

import com.rafikoko.userprofileservice.exception.ResourceNotFoundException;
import com.rafikoko.userprofileservice.model.UserProfile;
import com.rafikoko.userprofileservice.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserProfileServiceTest {

    private UserProfileRepository userProfileRepository;
    private UserProfileService userProfileService;

    @BeforeEach
    public void setup() {
        userProfileRepository = Mockito.mock(UserProfileRepository.class);
        userProfileService = new UserProfileService(userProfileRepository);
    }

    @Test
    public void testGetProfileById_Found() {
        UserProfile profile = UserProfile.builder()
                .id(1L)
                .firstName("rafi")
                .lastName("k")
                .email("rafi.k@example.com")
                .build();
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(profile));

        UserProfile found = userProfileService.getProfileById(1L);
        assertEquals("rafi", found.getFirstName());
        verify(userProfileRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProfileById_NotFound() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userProfileService.getProfileById(1L));
        verify(userProfileRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateUserProfile() {
        UserProfile profile = UserProfile.builder()
                .firstName("rafi")
                .lastName("k")
                .email("rafi.k@example.com")
                .phone("9876543210")
                .build();

        when(userProfileRepository.save(profile)).thenReturn(
                UserProfile.builder().id(1L)
                        .firstName("rafi")
                        .lastName("k")
                        .email("rafi.k@example.com")
                        .phone("9876543210")
                        .build());

        UserProfile created = userProfileService.createUserProfile(profile);
        assertNotNull(created.getId());
        assertEquals("rafi", created.getFirstName());
        verify(userProfileRepository, times(1)).save(profile);
    }

    @Test
    public void testUpdateUserProfile() {
        Long id = 1L;
        UserProfile existingProfile = UserProfile.builder()
                .id(id)
                .firstName("Rafal")
                .lastName("K")
                .email("rafal.k@example.com")
                .phone("1112223333")
                .build();

        UserProfile updateData = UserProfile.builder()
                .firstName("Pawel")
                .lastName("k")
                .email("pawel.k@example.com")
                .phone("4445556666")
                .build();

        when(userProfileRepository.findById(id)).thenReturn(Optional.of(existingProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserProfile updated = userProfileService.updateUserProfile(id, updateData);
        assertEquals("Pawel", updated.getFirstName());
        assertEquals("pawel.k@example.com", updated.getEmail());
        verify(userProfileRepository, times(1)).findById(id);
        verify(userProfileRepository, times(1)).save(existingProfile);
    }

    @Test
    public void testDeleteUserProfile() {
        Long id = 1L;
        UserProfile existingProfile = UserProfile.builder()
                .id(id)
                .firstName("Pawel")
                .lastName("P")
                .email("pawel.p@example.com")
                .build();

        when(userProfileRepository.findById(id)).thenReturn(Optional.of(existingProfile));
        doNothing().when(userProfileRepository).delete(existingProfile);

        userProfileService.deleteUserProfile(id);
        verify(userProfileRepository, times(1)).findById(id);
        verify(userProfileRepository, times(1)).delete(existingProfile);
    }

    @Test
    public void testCreateUserProfile_EmailAlreadyExists() {
        UserProfile profile = UserProfile.builder()
                .firstName("Pawel")
                .lastName("P")
                .email("pawel.p@example.com")
                .phone("9876543210")
                .build();

        // Simulate that the email is already in use
        when(userProfileRepository.findByEmail(profile.getEmail()))
                .thenReturn(Optional.of(profile));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            userProfileService.createUserProfile(profile)
        );

        assertEquals("Email already exists: " + profile.getEmail(), exception.getMessage());
        // Ensure save is never called due to duplicate email
        verify(userProfileRepository, times(0)).save(any(UserProfile.class));
    }

}
