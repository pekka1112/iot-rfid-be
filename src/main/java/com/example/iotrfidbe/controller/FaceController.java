package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.entity.Faces;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.repository.FaceRepository;
import com.example.iotrfidbe.repository.ResidentRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faces")
@RequiredArgsConstructor
public class FaceController {

    private final FaceRepository faceRepository;
    private final ResidentRepository residentRepository;

    /** GET /api/faces - Lay tat ca face records */
    @GetMapping
    public ResponseEntity<List<Faces>> getAll() {
        return ResponseEntity.ok(faceRepository.findAll());
    }

    /** GET /api/faces/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Faces> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(faceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Face not found: " + id)));
    }

    /** GET /api/faces/resident/{residentId} - Lay tat ca anh khuon mat cua cu dan */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<Faces>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(faceRepository.findByResident_ResidentId(residentId));
    }

    /** POST /api/faces?residentId={id} - Them face record cho cu dan */
    @PostMapping
    public ResponseEntity<Faces> create(@RequestParam Integer residentId, @RequestBody Faces face) {
        Residents resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new RuntimeException("Resident not found: " + residentId));
        face.setResident(resident);
        return ResponseEntity.status(HttpStatus.CREATED).body(faceRepository.save(face));
    }

    /** PUT /api/faces/{id} - Cap nhat face record */
    @PutMapping("/{id}")
    public ResponseEntity<Faces> update(@PathVariable Integer id, @RequestBody Faces dto) {
        Faces face = faceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Face not found: " + id));
        if (dto.getFaceImage() != null) face.setFaceImage(dto.getFaceImage());
        if (dto.getFaceEmbedding() != null) face.setFaceEmbedding(dto.getFaceEmbedding());
        return ResponseEntity.ok(faceRepository.save(face));
    }

    /** DELETE /api/faces/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        faceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
