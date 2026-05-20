package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.request.ResidentDetailRequest;
import com.example.iotrfidbe.entity.ResidentDetail;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.ResidentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResidentDetailService {

    private final ResidentDetailRepository residentDetailRepository;

    // ==================== MAPPERS ====================

    private ResidentDetailRequest toDTO(ResidentDetail entity) {
        if (entity == null) return null;

        List<ResidentDetailRequest.VehicleRequest> vehicleRequests = new ArrayList<>();
        if (entity.getVehicles() != null) {
            vehicleRequests = entity.getVehicles().stream()
                    .map(v -> new ResidentDetailRequest.VehicleRequest(v.getLicensePlate(), v.getVehicleType()))
                    .collect(Collectors.toList());
        }

        return ResidentDetailRequest.builder()
                .residentId(entity.getResidentId())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .birthYear(entity.getBirthYear())
                .status(entity.getStatus())
                .vehicles(vehicleRequests)
                .build();
    }

    private ResidentDetail toEntity(ResidentDetailRequest request) {
        if (request == null) return null;

        ResidentDetail entity = new ResidentDetail();
        entity.setResidentId(request.getResidentId());
        entity.setFullName(request.getFullName());
        entity.setPhone(request.getPhone());
        entity.setBirthYear(request.getBirthYear());
        entity.setStatus(request.getStatus() != null ? request.getStatus() : "active");

        List<Vehicles> vehiclesList = new ArrayList<>();
        if (request.getVehicles() != null) {
            vehiclesList = request.getVehicles().stream()
                    .map(vr -> {
                        Vehicles v = new Vehicles();
                        v.setLicensePlate(vr.getLicensePlate());
                        v.setVehicleType(vr.getVehicleType());
                        return v;
                    })
                    .collect(Collectors.toList());
        }
        entity.setVehicles(vehiclesList);

        return entity;
    }

    // ==================== CRUD OPERATIONS ====================

    @Transactional(readOnly = true)
    public List<ResidentDetailRequest> getAll() {
        return residentDetailRepository.findAllWithVehicles().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResidentDetailRequest getById(Integer id) {
        ResidentDetail entity = residentDetailRepository.findByIdWithVehicles(id)
                .orElseThrow(() -> new RuntimeException("ResidentDetail not found with ID: " + id));
        return toDTO(entity);
    }

    @Transactional
    public ResidentDetailRequest create(ResidentDetailRequest request) {
        if (request.getResidentId() != null && residentDetailRepository.existsById(request.getResidentId())) {
            throw new RuntimeException("ResidentDetail with ID " + request.getResidentId() + " already exists.");
        }
        ResidentDetail entity = toEntity(request);
        ResidentDetail saved = residentDetailRepository.save(entity);
        return toDTO(saved);
    }

    @Transactional
    public ResidentDetailRequest update(Integer id, ResidentDetailRequest request) {
        ResidentDetail existing = residentDetailRepository.findByIdWithVehicles(id)
                .orElseThrow(() -> new RuntimeException("ResidentDetail not found with ID: " + id));

        if (request.getFullName() != null) {
            existing.setFullName(request.getFullName());
        }
        if (request.getPhone() != null) {
            existing.setPhone(request.getPhone());
        }
        if (request.getBirthYear() != null) {
            existing.setBirthYear(request.getBirthYear());
        }
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        // Handle vehicles update
        if (request.getVehicles() != null) {
            existing.getVehicles().clear();
            List<Vehicles> newVehicles = request.getVehicles().stream()
                    .map(vr -> {
                        Vehicles v = new Vehicles();
                        v.setLicensePlate(vr.getLicensePlate());
                        v.setVehicleType(vr.getVehicleType());
                        return v;
                    })
                    .collect(Collectors.toList());
            existing.getVehicles().addAll(newVehicles);
        }

        ResidentDetail saved = residentDetailRepository.save(existing);
        return toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!residentDetailRepository.existsById(id)) {
            throw new RuntimeException("ResidentDetail not found with ID: " + id);
        }
        residentDetailRepository.deleteById(id);
    }
}
