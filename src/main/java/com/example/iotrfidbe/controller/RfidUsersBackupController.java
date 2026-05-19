package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.entity.RfidUsersBackup;
import com.example.iotrfidbe.service.RfidUsersBackupService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rfid-users-backups")
@RequiredArgsConstructor
public class RfidUsersBackupController {

    private final RfidUsersBackupService service;

    @GetMapping
    public ResponseEntity<List<RfidUsersBackup>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RfidUsersBackup> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<RfidUsersBackup> create(@RequestBody RfidUsersBackup rfidUsersBackup) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(rfidUsersBackup));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RfidUsersBackup> update(@PathVariable Integer id, @RequestBody RfidUsersBackup dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
