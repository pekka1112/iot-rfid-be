package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.AccessLogDTO;
import com.example.iotrfidbe.entity.AccessLogs;
import com.example.iotrfidbe.entity.Cameras;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.AccessLogRepository;
import com.example.iotrfidbe.repository.CameraRepository;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccessLogService {

    private final AccessLogRepository accessLogRepository;
    private final ResidentRepository residentRepository;
    private final VehicleRepository vehicleRepository;
    private final CameraRepository cameraRepository;

    // ==================== Mapper ====================

    private AccessLogDTO toDTO(AccessLogs log) {
        return AccessLogDTO.builder()
                .logId(log.getLogId())
                .residentId(log.getResident() != null ? log.getResident().getResidentId() : null)
                .residentName(log.getResident() != null ? log.getResident().getFullName() : null)
                .vehicleId(log.getVehicle() != null ? log.getVehicle().getVehicleId() : null)
                .vehiclePlate(log.getVehicle() != null ? log.getVehicle().getLicensePlate() : null)
                .cameraId(log.getCamera() != null ? log.getCamera().getCameraId() : null)
                .cameraName(log.getCamera() != null ? log.getCamera().getCameraName() : null)
                .detectedPlate(log.getDetectedPlate())
                .faceMatch(log.getFaceMatch())
                .plateMatch(log.getPlateMatch())
                .direction(log.getDirection())
                .imageFace(log.getImageFace())
                .imagePlate(log.getImagePlate())
                .createdAt(log.getCreatedAt())
                .build();
    }

    // ==================== CRUD ====================

    public List<AccessLogDTO> getAll() {
        return accessLogRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AccessLogDTO getById(Integer id) {
        AccessLogs log = accessLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AccessLog not found: " + id));
        return toDTO(log);
    }

    public List<AccessLogDTO> getByResident(Integer residentId) {
        return accessLogRepository.findByResident_ResidentIdOrderByCreatedAtDesc(residentId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<AccessLogDTO> getByDateRange(LocalDateTime from, LocalDateTime to) {
        return accessLogRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(from, to)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public AccessLogDTO create(AccessLogDTO dto) {
        AccessLogs log = new AccessLogs();

        if (dto.getResidentId() != null) {
            Residents resident = residentRepository.findById(dto.getResidentId())
                    .orElse(null);
            log.setResident(resident);
        }
        if (dto.getVehicleId() != null) {
            Vehicles vehicle = vehicleRepository.findById(dto.getVehicleId())
                    .orElse(null);
            log.setVehicle(vehicle);
        }
        if (dto.getCameraId() != null) {
            Cameras camera = cameraRepository.findById(dto.getCameraId())
                    .orElse(null);
            log.setCamera(camera);
        }

        log.setDetectedPlate(dto.getDetectedPlate());
        log.setFaceMatch(dto.getFaceMatch());
        log.setPlateMatch(dto.getPlateMatch());
        log.setDirection(dto.getDirection());
        log.setImageFace(dto.getImageFace());
        log.setImagePlate(dto.getImagePlate());

        return toDTO(accessLogRepository.save(log));
    }

    @Transactional
    public void delete(Integer id) {
        if (!accessLogRepository.existsById(id)) {
            throw new RuntimeException("AccessLog not found: " + id);
        }
        accessLogRepository.deleteById(id);
    }
}
