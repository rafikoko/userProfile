package com.rafikoko.userprofileservice.controller;

import com.rafikoko.userprofileservice.model.UserProfile;
import com.rafikoko.userprofileservice.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        userProfileRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetProfile() throws Exception {
        UserProfile profile = UserProfile.builder()
                .firstName("Rafal")
                .lastName("K")
                .email("rafal.k@example.com")
                .phone("1234567890")
                .build();

        // Create profile
        mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Rafal"));

        // Get all profiles anc check if only one exists
        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testUpdateProfile() throws Exception {
        // Create a profile first
        UserProfile profile = UserProfile.builder()
                .firstName("Rafal")
                .lastName("K")
                .email("rafal.k@example.com")
                .phone("2223334444")
                .build();

        String content = objectMapper.writeValueAsString(profile);
        String response = mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserProfile createdProfile = objectMapper.readValue(response, UserProfile.class);

        // Prepare update data
        UserProfile updateData = UserProfile.builder()
                .firstName("Pawel")
                .lastName("K")
                .email("pawel.k@example.com")
                .phone("5556667777")
                .build();

        mockMvc.perform(put("/profiles/{id}", createdProfile.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Pawel")))
                .andExpect(jsonPath("$.email", is("pawel.k@example.com")));
    }

    @Test
    public void testDeleteProfile() throws Exception {
        // Create a profile to delete
        UserProfile profile = UserProfile.builder()
                .firstName("Rafal")
                .lastName("K")
                .email("rafal.k@example.com")
                .phone("8889990000")
                .build();

        String content = objectMapper.writeValueAsString(profile);
        String response = mockMvc.perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserProfile createdProfile = objectMapper.readValue(response, UserProfile.class);

        // Delete the profile and expect no content
        mockMvc.perform(delete("/profiles/{id}", createdProfile.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion by checking that profile list is empty
        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
