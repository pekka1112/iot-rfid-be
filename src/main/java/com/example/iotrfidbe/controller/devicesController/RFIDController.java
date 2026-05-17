package com.example.iotrfidbe.controller.devicesController;

import com.example.iotrfidbe.dto.request.RFIDRequest;
import com.example.iotrfidbe.dto.response.RFIDResponse;
import com.example.iotrfidbe.service.rfidService.RFIDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rfid")
@RequiredArgsConstructor
public class RFIDController {

    private final RFIDService rfidService;

    @PostMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestBody RFIDRequest request
    ) {

        RFIDResponse response =
                rfidService.verify(
                        request.getCard_uid(),
                        request.getDetected_plate()
                );

        return ResponseEntity.ok(response);
    }
}