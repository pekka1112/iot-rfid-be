package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.AccessLogDTO;
import com.example.iotrfidbe.dto.request.AccessLogRequest;
import com.example.iotrfidbe.entity.AccessLogs;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.AccessLogRepository;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import com.example.iotrfidbe.service.AccessLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController // mark return JSON Data, not HTML
@RequestMapping("/api/access-logs") // prefix của tất cả API class
@RequiredArgsConstructor // lombok lib
public class AccessLogController {

    private final AccessLogService accessLogService; // Inject Service - process all logic in service, not controller
    private final AccessLogRepository accessLogRepository;
    private final ResidentRepository residentRepository;
    private final VehicleRepository vehicleRepository;

    /**
     * call to service to impl method get all access log from DB
     * @return: DTO filter by AccessLog
     */
    @GetMapping
    public ResponseEntity<List<AccessLogDTO>> getAll() {
        return ResponseEntity.ok(accessLogService.getAll());
    }

    /**
     * get accessLog by logId
     * @param id of accesslog
     * @return DTO filter by AccessLog
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccessLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(accessLogService.getById(id));
    }

    /**
     * get resident by resident Id
     * @param residentId
     * @return DTO filter by AccessLog
     */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<AccessLogDTO>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(accessLogService.getByResident(residentId));
    }

    /**
     * GET /api/access-logs/range?from=2024-01-01T00:00:00&to=2024-12-31T23:59:59
     * Loc nhat ky theo khoang thoi gian
     */
    @GetMapping("/range")
    public ResponseEntity<List<AccessLogDTO>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(accessLogService.getByDateRange(from, to));
    }

    /** POST /api/access-logs - Tao moi nhat ky (thu cong hoac tu he thong camera) */
    @PostMapping
    public ResponseEntity<AccessLogDTO> create(@RequestBody AccessLogDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accessLogService.create(dto));
    }

    /** DELETE /api/access-logs/{id} - Xoa nhat ky */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accessLogService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/save")
//    public String saveLog(
//            @RequestBody AccessLogRequest request
//    ) {
//
//        Residents resident = residentRepository
//                .findById(request.getResident_id())
//                .orElseThrow(() ->
//                        new RuntimeException("Resident not found"));
//
//        Vehicles vehicle = vehicleRepository
//                .findById(request.getVehicle_id())
//                .orElseThrow(() ->
//                        new RuntimeException("Vehicle not found"));
//
//        AccessLogs log = new AccessLogs();
//
//        // 🔥 SET OBJECT
//        log.setResident(resident);
//        log.setVehicle(vehicle);
//        log.setDetectedPlate(request.getDetected_plate());
//        log.setFaceMatch(request.getFace_match());
//        log.setPlateMatch(request.getPlate_match());
//        log.setDirection(request.getDirection());
//        log.setCreatedAt(LocalDateTime.now());
//        accessLogRepository.save(log);
//        return "OK";
//    }


    @PostMapping("/save")
    public ResponseEntity<AccessLogDTO> saveLog(
            @RequestBody AccessLogRequest request
    ) {

        AccessLogs log = new AccessLogs();

        // =================================================
        // RESIDENT
        // =================================================
        Residents resident = null;

        if (request.getResident_id() != null) {

            resident = residentRepository
                    .findById(request.getResident_id())
                    .orElse(null);

            log.setResident(resident);
        }

        // =================================================
        // VEHICLE
        // =================================================
        Vehicles vehicle = null;

        if (request.getVehicle_id() != null) {

            vehicle = vehicleRepository
                    .findById(request.getVehicle_id())
                    .orElse(null);

            log.setVehicle(vehicle);
        }

        // =================================================
        // BASIC
        // =================================================
        log.setDetectedPlate(request.getDetected_plate());

        log.setFaceMatch(request.getFace_match());

        log.setPlateMatch(request.getPlate_match());

        log.setDirection(request.getDirection());

        log.setCreatedAt(LocalDateTime.now());

        // =================================================
        // SAVE
        // =================================================
        AccessLogs saved = accessLogRepository.save(log);

        // =================================================
        // RETURN DTO
        // =================================================
        AccessLogDTO dto = new AccessLogDTO();

        dto.setLogId(saved.getLogId());

        dto.setResidentId(
                resident != null ? resident.getResidentId() : null
        );

        dto.setResidentName(
                request.getResident_name()
        );

        dto.setVehicleId(
                vehicle != null ? vehicle.getVehicleId() : null
        );

        dto.setDetectedPlate(saved.getDetectedPlate());

        dto.setFaceMatch(saved.getFaceMatch());

        dto.setPlateMatch(saved.getPlateMatch());

        dto.setDirection(saved.getDirection());

        dto.setCreatedAt(saved.getCreatedAt());

        // ===== NEW =====
        dto.setEnrollid(request.getEnrollid());

        dto.setDbPlates(request.getDb_plates());

        dto.setFailReason(request.getFail_reason());

        dto.setIsCorrectFaceAndPlate(
                request.getIsCorrectFaceAndPlate()
        );

        dto.setTimestamp(request.getTimestamp());

        return ResponseEntity.ok(dto);
    }
}
