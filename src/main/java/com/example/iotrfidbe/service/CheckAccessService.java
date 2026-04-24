package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.CheckResidentRequest;
import com.example.iotrfidbe.dto.CheckResidentResponse;
import com.example.iotrfidbe.entity.AccessLogs;
import com.example.iotrfidbe.entity.DeviceBackups;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.AccessLogRepository;
import com.example.iotrfidbe.repository.DeviceBackupRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service xu ly logic kiem tra cu dan qua FaceID / Van tay + Bien so xe.
 *
 * Luong:
 *  1. Thiet bi nhan dien khuon mat / van tay → tra ve deviceUserId
 *  2. Camera bien so → doc duoc licensePlate
 *  3. Backend:
 *     a. Tim device_backups theo deviceUserId → lay resident (bang FaceID/van tay)
 *     b. Tim vehicles theo licensePlate → lay resident_id cua chu xe
 *     c. Kiem tra 2 resident_id co khop nhau
 *     d. Neu khop → grant access, ghi access_log
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CheckAccessService {

    private final DeviceBackupRepository deviceBackupRepository;
    private final VehicleRepository vehicleRepository;
    private final AccessLogRepository accessLogRepository;

    @Transactional // dùng Transaction chủ yếu để ghi Log cho thống nhất
    public CheckResidentResponse checkResident(CheckResidentRequest request) {

        // thực hiện ghi log vào hệ thống
        log.info("Checking resident: deviceUserId={}, plate={}",
                request.getDeviceUserId(), request.getLicensePlate());

        // Step 1: Tim device backup → resident qua FaceID / van tay
        // get user trong DB cua thietbi trc -> assign vao bien deviceBackupOpt
        Optional<DeviceBackups> deviceBackupOpt = deviceBackupRepository.findByDeviceUserId(request.getDeviceUserId());
        // khi trong thiet bi khong co user do thi check vao DB cua Server de tim
        if (deviceBackupOpt.isEmpty()) {
            log.warn("No device backup found for deviceUserId={}", request.getDeviceUserId());  // ghi log
            return CheckResidentResponse.builder()
                    .isResident(false)
                    .grantAccess(false)
                    .message("Khong tim thay nguoi dung voi ID nhan dang nay tren he thong.")
                    .build();
        }

        DeviceBackups deviceBackup = deviceBackupOpt.get();
        Residents residentFromFace = deviceBackup.getResident();// lay ra cu dan tu thong tin backup

        if (residentFromFace == null) {
            return CheckResidentResponse.builder() // dung builder pattern de tao ra 1 cu dan
                    .isResident(false)
                    .grantAccess(false)
                    .message("Nguoi dung nhan dien khong lien ket voi cu dan nao.")
                    .build();
        }

        // Step 2: Tim xe theo bien so → lay chu xe
        Optional<Vehicles> vehicleOpt = vehicleRepository.findByLicensePlate(request.getLicensePlate());

        boolean plateMatch = false;
        boolean grantAccess = false;
        Vehicles vehicle = null;

        if (vehicleOpt.isEmpty()) {
            log.warn("License plate not found: {}", request.getLicensePlate());
        } else {
            vehicle = vehicleOpt.get();
            Residents residentFromPlate = vehicle.getResident();

            // Step 3: So khop resident_id tu FaceID va bien so xe
            if (residentFromPlate != null &&
                    residentFromPlate.getResidentId().equals(residentFromFace.getResidentId())) {
                plateMatch = true;
                grantAccess = true;
                log.info("Access GRANTED for residentId={}", residentFromFace.getResidentId());
            } else {
                log.warn("Plate belongs to different resident. Access DENIED.");
            }
        }

        // Step 4: Ghi access log
        saveAccessLog(residentFromFace, vehicle, request.getLicensePlate(), true, plateMatch, grantAccess);

        String message = grantAccess
                ? "Cu dan hop le. Mo cua."
                : (vehicleOpt.isEmpty()
                ? "Bien so xe khong co trong he thong. Tu choi."
                : "Bien so xe khong khop voi nguoi nhan dien. Tu choi.");

        return CheckResidentResponse.builder()
                .isResident(true)
                .residentId(residentFromFace.getResidentId())
                .fullName(residentFromFace.getFullName())
                .grantAccess(grantAccess)
                .message(message)
                .build();
    }

    private void saveAccessLog(Residents resident, Vehicles vehicle, String detectedPlate,
            boolean faceMatch, boolean plateMatch, boolean grantAccess) {
        try {
            AccessLogs log = new AccessLogs();
            log.setResident(resident);
            log.setVehicle(vehicle);
            log.setDetectedPlate(detectedPlate);
            log.setFaceMatch(faceMatch);
            log.setPlateMatch(plateMatch);
            log.setDirection("IN");
            accessLogRepository.save(log);
        } catch (Exception e) {
            log.error("Failed to save access log", e);
        }
    }
}
