package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.request.FaceDirectionRequest;
import com.example.iotrfidbe.entity.FaceLog;
import com.example.iotrfidbe.repository.FaceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaceDirectionService {

    private final FaceLogRepository faceLogRepository;

    public Map<String, Object> detectDirection(FaceDirectionRequest request) {

        Optional<FaceLog> existing =
                faceLogRepository.findByFaceIdAndPlateNumber(
                        request.getFaceId(),
                        request.getPlateNumber()
                );

        Map<String, Object> response = new HashMap<>();

        // ===== XE RA =====
        if (existing.isPresent()) {

            faceLogRepository.delete(existing.get());

            response.put("direction", "OUT");
            response.put("message", "Xe đi ra");

            return response;
        }

        // ===== XE VÀO =====
        FaceLog log = FaceLog.builder()
                .faceId(request.getFaceId())
                .plateNumber(request.getPlateNumber())
                .createdAt(LocalDateTime.now())
                .build();

        faceLogRepository.save(log);

        response.put("direction", "IN");
        response.put("message", "Xe đi vào");

        return response;
    }
}