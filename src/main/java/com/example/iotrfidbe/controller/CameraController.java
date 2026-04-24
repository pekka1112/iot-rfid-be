package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.entity.Cameras;
import com.example.iotrfidbe.repository.CameraRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cameras")
@RequiredArgsConstructor
public class CameraController {

    private final CameraRepository cameraRepository;

    /** GET /api/cameras */
    @GetMapping
    public ResponseEntity<List<Cameras>> getAll() {
        return ResponseEntity.ok(cameraRepository.findAll());
    }

    /** GET /api/cameras/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Cameras> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cameraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camera not found: " + id)));
    }

    /** POST /api/cameras */
    @PostMapping
    public ResponseEntity<Cameras> create(@RequestBody Cameras camera) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cameraRepository.save(camera));
    }

    /** PUT /api/cameras/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<Cameras> update(@PathVariable Integer id, @RequestBody Cameras dto) {
        Cameras cam = cameraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camera not found: " + id));
        if (dto.getCameraName() != null) cam.setCameraName(dto.getCameraName());
        if (dto.getLocation() != null) cam.setLocation(dto.getLocation());
        if (dto.getDirection() != null) cam.setDirection(dto.getDirection());
        return ResponseEntity.ok(cameraRepository.save(cam));
    }

    /** DELETE /api/cameras/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cameraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
