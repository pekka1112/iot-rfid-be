package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.FaceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FaceLogRepository extends JpaRepository<FaceLog, Integer> {

    Optional<FaceLog> findByFaceIdAndPlateNumber(
            Integer faceId,
            String plateNumber
    );
}