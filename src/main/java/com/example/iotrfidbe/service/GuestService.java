package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.GuestCheckResponse;
import com.example.iotrfidbe.dto.GuestCheckinRequest;
import com.example.iotrfidbe.dto.GuestCheckoutRequest;
import com.example.iotrfidbe.entity.RfidUsersBackups;
import com.example.iotrfidbe.repository.RfidUsersBackupRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service xu ly luong check-in / check-out cho khach van lai.
 *
 * Check-in:
 *   - Khach quet the RFID luc vao, the da chua san bien so xe
 *   - Luu record moi vao rfid_users_backups voi status='in_building'
 *   - Mo cua cho vao
 *
 * Check-out:
 *   - Khach quet lai the RFID luc ra
 *   - Tim record check-in theo cardUid + status='in_building'
 *   - Kiem tra bien so xe co khop khong
 *   - Neu khop: cap nhat check_out_at, status='departed', mo cua
 *   - Neu khong khop: tu choi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GuestService {

    private final RfidUsersBackupRepository guestRepository;

    // ===================== Check-in =====================

    @Transactional
    public GuestCheckResponse checkin(GuestCheckinRequest request) {
        log.info("Guest checkin: cardUid={}, plate={}", request.getCardUid(), request.getLicensePlate());

        // Kiem tra xem the nay co dang trong toa nha chua (tranh quet 2 lan)
        Optional<RfidUsersBackups> existing = guestRepository
                .findTopByCardUidAndStatusOrderByCheckInAtDesc(request.getCardUid(), "in_building");

        if (existing.isPresent()) {
            RfidUsersBackups prev = existing.get();
            log.warn("Card {} already checked in at {}", request.getCardUid(), prev.getCheckInAt());
            return GuestCheckResponse.builder()
                    .grantAccess(false)
                    .guestId(prev.getGuestId())
                    .cardUid(request.getCardUid())
                    .licensePlate(prev.getLicensePlate())
                    .message("The nay dang co nguoi su dung trong toa nha. Khong the check-in them.")
                    .build();
        }

        // Tao record check-in moi
        RfidUsersBackups guest = new RfidUsersBackups();
        guest.setCardUid(request.getCardUid());
        guest.setLicensePlate(request.getLicensePlate());
        guest.setGateInId(request.getGateInId());
        guest.setStatus("in_building");
        guest.setCheckInAt(LocalDateTime.now());

        RfidUsersBackups saved = guestRepository.save(guest);
        log.info("Guest checked in, guestId={}", saved.getGuestId());

        return GuestCheckResponse.builder()
                .grantAccess(true)
                .guestId(saved.getGuestId())
                .cardUid(saved.getCardUid())
                .licensePlate(saved.getLicensePlate())
                .message("Khach vao thanh cong. Mo cua.")
                .build();
    }

    // ===================== Check-out =====================

    @Transactional
    public GuestCheckResponse checkout(GuestCheckoutRequest request) {
        log.info("Guest checkout: cardUid={}, plate={}", request.getCardUid(), request.getLicensePlate());

        // Tim record check-in dang con trong toa nha
        Optional<RfidUsersBackups> guestOpt = guestRepository
                .findTopByCardUidAndStatusOrderByCheckInAtDesc(request.getCardUid(), "in_building");

        if (guestOpt.isEmpty()) {
            log.warn("No active check-in found for cardUid={}", request.getCardUid());
            return GuestCheckResponse.builder()
                    .grantAccess(false)
                    .cardUid(request.getCardUid())
                    .licensePlate(request.getLicensePlate())
                    .message("Khong tim thay ban ghi check-in hop le cho the nay.")
                    .build();
        }

        RfidUsersBackups guest = guestOpt.get();

        // Kiem tra bien so xe co khop khong (bao mat: chong doi the)
        if (!guest.getLicensePlate().equalsIgnoreCase(request.getLicensePlate().trim())) {
            log.warn("Plate mismatch: expected={}, got={}", guest.getLicensePlate(), request.getLicensePlate());
            return GuestCheckResponse.builder()
                    .grantAccess(false)
                    .guestId(guest.getGuestId())
                    .cardUid(request.getCardUid())
                    .licensePlate(request.getLicensePlate())
                    .message("Bien so xe khong khop voi the RFID. Tu choi ra cua.")
                    .build();
        }

        // Hop le: cap nhat trang thai va mo cua
        guest.setCheckOutAt(LocalDateTime.now());
        guest.setStatus("departed");
        guest.setGateOutId(request.getGateOutId());
        guestRepository.save(guest);

        log.info("Guest checked out successfully, guestId={}", guest.getGuestId());

        return GuestCheckResponse.builder()
                .grantAccess(true)
                .guestId(guest.getGuestId())
                .cardUid(request.getCardUid())
                .licensePlate(request.getLicensePlate())
                .message("Xac thuc thanh cong. Mo cua ra.")
                .build();
    }

    // ===================== Query =====================

    public List<RfidUsersBackups> getAllGuests() {
        return guestRepository.findAllByOrderByCheckInAtDesc();
    }

    public List<RfidUsersBackups> getGuestsInBuilding() {
        return guestRepository.findByStatus("in_building");
    }
}
