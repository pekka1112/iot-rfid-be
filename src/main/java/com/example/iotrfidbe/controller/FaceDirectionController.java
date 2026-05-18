package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.request.FaceDirectionRequest;
import com.example.iotrfidbe.service.FaceDirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/face")
@RequiredArgsConstructor
public class FaceDirectionController {

    private final FaceDirectionService service;

    @PostMapping("/direction")
    public Map<String, Object> detectDirection(
            @RequestBody FaceDirectionRequest request
    ) {
        return service.detectDirection(request);
    }
}