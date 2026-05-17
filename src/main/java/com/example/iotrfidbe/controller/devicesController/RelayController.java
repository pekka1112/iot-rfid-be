package com.example.iotrfidbe.controller.devicesController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/relay")
public class RelayController {

    private String relayState = "CLOSE";

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "state",
                relayState
        );
    }

    @PostMapping("/open")
    public String openRelay() {
        relayState = "OPEN";
        return "OPEN";
    }

    @PostMapping("/close")
    public String closeRelay() {
        relayState = "CLOSE";
        return "CLOSE";
    }
}