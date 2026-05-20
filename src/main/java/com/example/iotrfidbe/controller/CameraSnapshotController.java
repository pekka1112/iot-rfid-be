package com.example.iotrfidbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/camera")
@CrossOrigin(origins = "*")
public class CameraSnapshotController {

    @GetMapping("/snapshot")
    public ResponseEntity<byte[]> getSnapshot(@RequestParam(required = false) Integer camId) {
        try {
            // Dahua camera IP and snapshot endpoint
            String cameraUrl = "http://192.168.1.108/cgi-bin/snapshot.cgi";
            RestTemplate restTemplate = new RestTemplate();
            
            // Set up Basic Authentication header manually
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("admin", "Abc123456");
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            
            // Fetch snapshot image from camera
            ResponseEntity<byte[]> response = restTemplate.exchange(
                cameraUrl,
                HttpMethod.GET,
                entity,
                byte[].class
            );
            
            byte[] imageBytes = response.getBody();
            if (imageBytes == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            
            // Return image as JPEG
            return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
                
        } catch (Exception e) {
            System.err.println("Error fetching camera snapshot from proxy: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
