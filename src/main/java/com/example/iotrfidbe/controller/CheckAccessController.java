package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.CheckResidentRequest;
import com.example.iotrfidbe.dto.CheckResidentResponse;
import com.example.iotrfidbe.service.CheckAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xu ly kiem tra nhận dien cu dan.
 *
 * POST /api/check/resident
 *   Input: { "deviceUserId": 101, "licensePlate": "59-A1 123.45" }
 *   - deviceUserId: ID nguoi dung tren thiet bi nhan dien (FaceID/van tay)
 *   - licensePlate: Bien so xe doc tu camera
 *   Output: { isResident, residentId, fullName, grantAccess, message }
 */
@RestController
@RequestMapping("/api/check")
@RequiredArgsConstructor
public class CheckAccessController {

    private final CheckAccessService checkAccessService;

    /**
     * Kiem tra cu dan dang hien thi tren camera co phai cu dan trong CSDL khong.
     * Ket hop FaceID (deviceUserId tu may nhan dien) + bien so xe tu camera.
     */
    @PostMapping("/resident")
    public ResponseEntity<CheckResidentResponse> checkResident(@RequestBody CheckResidentRequest request) {
        CheckResidentResponse response = checkAccessService.checkResident(request);
        return ResponseEntity.ok(response);
    }
}
