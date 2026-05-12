package com.example.iotrfidbe.controller.backupDataController;

import com.example.iotrfidbe.dto.AccessLogDTO;
import com.example.iotrfidbe.service.AccessLogService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.example.iotrfidbe.service.backupdata.UsersBackupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class BackupDataUsersController {

    @Autowired
    private UsersBackupService usersBackupService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody Map<String, Object> data) {

        System.out.println("sys _> get and save data from PyServer");
        System.out.println("sys _> raw " + data);

        Long id = Long.parseLong(data.get("id").toString());
        String name = data.get("name").toString();
        String base64 = data.get("base64").toString();

        usersBackupService.saveUser(id, name, base64);

        return ResponseEntity.ok("Saved Successfully");
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(
                usersBackupService.getAllUsersWithBase64()
        );
    }
}