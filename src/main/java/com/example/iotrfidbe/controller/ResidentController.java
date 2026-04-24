package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.ResidentDTO;
import com.example.iotrfidbe.service.ResidentService;
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
@RequestMapping("/api/residents")
@RequiredArgsConstructor
public class ResidentController {

    private final ResidentService residentService;

    /** GET /api/residents - Lay danh sach tat ca cu dan */
    @GetMapping
    public ResponseEntity<List<ResidentDTO>> getAll() {
        return ResponseEntity.ok(residentService.getAll());
    }

    /** GET /api/residents/{id} - Lay cu dan theo ID */
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(residentService.getById(id));
    }

    /** GET /api/residents/search?name=... - Tim kiem theo ten */
    @GetMapping("/search")
    public ResponseEntity<List<ResidentDTO>> search(@RequestParam String name) {
        return ResponseEntity.ok(residentService.searchByName(name));
    }

    /** GET /api/residents/status/{status} - Lay theo trang thai (active/inactive) */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ResidentDTO>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(residentService.getByStatus(status));
    }

    /** POST /api/residents - Tao moi cu dan */
    @PostMapping
    public ResponseEntity<ResidentDTO> create(@RequestBody ResidentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentService.create(dto));
    }

    /** PUT /api/residents/{id} - Cap nhat thong tin cu dan */
    @PutMapping("/{id}")
    public ResponseEntity<ResidentDTO> update(@PathVariable Integer id, @RequestBody ResidentDTO dto) {
        return ResponseEntity.ok(residentService.update(id, dto));
    }

    /** DELETE /api/residents/{id} - Xoa cu dan */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        residentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
