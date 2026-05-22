package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.VehiclesDTO;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesService {

    private final VehicleRepository vehicleRepository;
    private final ResidentRepository residentRepository;

    // ==================== MAPPER ====================

    private VehiclesDTO toDTO(Vehicles v) {
        VehiclesDTO dto = new VehiclesDTO();
        dto.setId(v.getVehicleId());
        dto.setPlateNumber(v.getLicensePlate());
        dto.setVehicleType(v.getVehicleType());
        if (v.getResident() != null) {
            dto.setResidentId(v.getResident().getResidentId());
            dto.setResidentName(v.getResident().getFullName());
        }
        return dto;
    }

    // ==================== APIs ====================

    /** Cập nhật xe theo biển số */
    @Transactional
    public VehiclesDTO updateVehicleByPlate(String licensePlate, VehiclesDTO request) {
        Vehicles vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe với biển số: " + licensePlate));
        // Cập nhật biển số mới nếu có và khác biển số cũ
        if (request.getPlateNumber() != null && !request.getPlateNumber().equals(licensePlate)) {
            vehicleRepository.findByLicensePlate(request.getPlateNumber())
                    .ifPresent(v -> {
                        throw new RuntimeException("Biển số xe đã tồn tại: " + request.getPlateNumber());
                    });
            vehicle.setLicensePlate(request.getPlateNumber());
        }
        if (request.getVehicleType() != null) {
            vehicle.setVehicleType(request.getVehicleType());
        }
        if (request.getResidentId() != null) {
            Residents resident = residentRepository.findById(request.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy cư dân với ID: " + request.getResidentId()));
            vehicle.setResident(resident);
        }
        return toDTO(vehicleRepository.save(vehicle));
    }

    /** Xóa xe theo biển số */
    @Transactional
    public void deleteVehicleByPlate(String licensePlate) {
        Vehicles vehicle = vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe với biển số: " + licensePlate));
        vehicleRepository.delete(vehicle);
    }

    /** Lấy tất cả xe */
    @Transactional(readOnly = true)
    public List<VehiclesDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    /** Thêm xe mới */
    @Transactional
    public VehiclesDTO createVehicle(VehiclesDTO request) {
        // Kiểm tra biển số đã tồn tại chưa
        vehicleRepository.findByLicensePlate(request.getPlateNumber())
                .ifPresent(v -> {
                    throw new RuntimeException("Biển số xe đã tồn tại: " + request.getPlateNumber());
                });

        Vehicles vehicle = new Vehicles();
        vehicle.setLicensePlate(request.getPlateNumber());
        vehicle.setVehicleType(request.getVehicleType());

        // Gắn cư dân nếu có residentId
        if (request.getResidentId() != null) {
            Residents resident = residentRepository.findById(request.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy cư dân với ID: " + request.getResidentId()));
            vehicle.setResident(resident);
        }

        return toDTO(vehicleRepository.save(vehicle));
    }

    /** Xóa xe theo ID */
    @Transactional
    public void deleteVehicle(Integer id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy xe với ID: " + id);
        }
        vehicleRepository.deleteById(id);
    }
}
