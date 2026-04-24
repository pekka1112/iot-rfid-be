package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.GuestCheckResponse;
import com.example.iotrfidbe.dto.GuestCheckinRequest;
import com.example.iotrfidbe.dto.GuestCheckoutRequest;
import com.example.iotrfidbe.entity.RfidUsersBackups;
import com.example.iotrfidbe.service.GuestService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xu ly luong check-in / check-out cua khach van lai.
 *
 * POST /api/guests/checkin
 *   Input: { "cardUid": "UID_GUEST_001", "licensePlate": "72-C1 555.55", "gateInId": 1 }
 *   Khach quet the RFID luc vao. The da chua san bien so xe.
 *
 * POST /api/guests/checkout
 *   Input: { "cardUid": "UID_GUEST_001", "licensePlate": "72-C1 555.55", "gateOutId": 2 }
 *   Khach quet lai the luc ra. He thong kiem tra bien so xe co khop khong.
 *   - Neu khop: grant access, cap nhat trang thai
 *   - Neu khong khop: tu choi
 */
@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    /**
     * Khach vao: quet the RFID luc di vao, luu record vao DB.
     * The RFID da duoc lap trinh san bien so xe cua khach.
     */
    @PostMapping("/checkin")
    public ResponseEntity<GuestCheckResponse> checkin(@RequestBody GuestCheckinRequest request) {
        return ResponseEntity.ok(guestService.checkin(request));
    }

    /**
     * Khach ra: quet the RFID, he thong so khop bien so xe.
     * Neu bien so xe khop voi record check-in truoc do thi mo cua.
     */
    @PostMapping("/checkout")
    public ResponseEntity<GuestCheckResponse> checkout(@RequestBody GuestCheckoutRequest request) {
        return ResponseEntity.ok(guestService.checkout(request));
    }

    /** GET /api/guests - Lay tat ca lich su khach van lai */
    @GetMapping
    public ResponseEntity<List<RfidUsersBackups>> getAll() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    /** GET /api/guests/in-building - Lay danh sach khach dang trong toa nha */
    @GetMapping("/in-building")
    public ResponseEntity<List<RfidUsersBackups>> getInBuilding() {
        return ResponseEntity.ok(guestService.getGuestsInBuilding());
    }
}
