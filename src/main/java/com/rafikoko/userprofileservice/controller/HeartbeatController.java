package com.rafikoko.userprofileservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeartbeatController {
    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("User Profile Service is up and running!");
    }
}
