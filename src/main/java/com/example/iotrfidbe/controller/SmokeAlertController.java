package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.SmokeAlertDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class SmokeAlertController {

    // Lưu status hiện tại trong memory
    private static SmokeAlertDTO currentSmokeAlert =
            new SmokeAlertDTO("CLEAR", "");

    // Python POST cảnh báo khói lên đây
    @PostMapping("/smoke")
    public void receiveSmokeAlert(@RequestBody SmokeAlertDTO alert) {
        System.out.println("✅ Nhận smoke alert: " + alert.getStatus());
        currentSmokeAlert = alert;  // Lưu status hiện tại
    }

    // React GET status hiện tại
    @GetMapping("/smoke/status")
    public SmokeAlertDTO getSmokeStatus() {
        return currentSmokeAlert;
    }
}
