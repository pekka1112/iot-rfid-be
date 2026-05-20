package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.request.ResidentDetailRequest;
import com.example.iotrfidbe.service.ResidentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resident-details")
@RequiredArgsConstructor
public class ResidentDetailController {

    private final ResidentDetailService residentDetailService;

    /** GET /api/resident-details - Lấy danh sách thông tin chi tiết cư dân */
    @GetMapping
    public ResponseEntity<List<ResidentDetailRequest>> getAll() {
        return ResponseEntity.ok(residentDetailService.getAll());
    }

    /** GET /api/resident-details/{id} - Lấy thông tin chi tiết cư dân theo ID */
    @GetMapping("/{id}")
    public ResponseEntity<ResidentDetailRequest> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(residentDetailService.getById(id));
    }

    /** POST /api/resident-details - Tạo mới thông tin cư dân kèm danh sách phương tiện */
    @PostMapping
    public ResponseEntity<ResidentDetailRequest> create(@RequestBody ResidentDetailRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(residentDetailService.create(request));
    }

    /** PUT /api/resident-details/{id} - Cập nhật thông tin cư dân kèm danh sách phương tiện */
    @PutMapping("/{id}")
    public ResponseEntity<ResidentDetailRequest> update(@PathVariable Integer id, @RequestBody ResidentDetailRequest request) {
        return ResponseEntity.ok(residentDetailService.update(id, request));
    }

    /** DELETE /api/resident-details/{id} - Xóa thông tin cư dân */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        residentDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
