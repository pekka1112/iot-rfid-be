package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.VehicleDTO;
import com.example.iotrfidbe.dto.VehiclesDTO;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import com.example.iotrfidbe.service.VehiclesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final ResidentRepository residentRepository;
    private final VehiclesService vehiclesService;

    private VehicleDTO toDTO(Vehicles v) {
        return VehicleDTO.builder()
                .vehicleId(v.getVehicleId())
                .residentId(v.getResident() != null ? v.getResident().getResidentId() : null)
                .residentName(v.getResident() != null ? v.getResident().getFullName() : null)
                .licensePlate(v.getLicensePlate())
                .vehicleType(v.getVehicleType())
                .createdAt(v.getCreatedAt())
                .build();
    }

    /** GET /api/vehicles - Lấy danh sách tất cả xe */
    @GetMapping
    public ResponseEntity<List<VehiclesDTO>> getAll() {
        return ResponseEntity.ok(vehiclesService.getAllVehicles());
    }

    /** POST /api/vehicles - Thêm một xe mới
     *  Body: { "plateNumber": "59-A1 123.45", "vehicleType": "Motorbike", "residentId": 1 }
     */
    @PostMapping
    public ResponseEntity<VehiclesDTO> create(@RequestBody VehiclesDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiclesService.createVehicle(request));
    }

    /** DELETE /api/vehicles/{id} - Xóa xe theo ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        vehiclesService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    /** GET /api/vehicles/{id} - Lấy chi tiết một xe theo ID */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Integer id) {
        Vehicles v = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + id));
        return ResponseEntity.ok(toDTO(v));
    }

    /** GET /api/vehicles/resident/{residentId} - Lấy danh sách xe của một cư dân */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<VehicleDTO>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(vehicleRepository.findByResident_ResidentId(residentId)
                .stream().map(this::toDTO).collect(Collectors.toList()));
    }

    /**
     * PUT /api/vehicles/plate/{licensePlate} - Cập nhật xe theo biển số
     * Body: { "plateNumber": "59-B2 999.99", "vehicleType": "Car", "residentId": 2 }
     */
    @PutMapping("/plate/{licensePlate}")
    public ResponseEntity<VehiclesDTO> updateByPlate(
            @PathVariable String licensePlate,
            @RequestBody VehiclesDTO request) {
        return ResponseEntity.ok(vehiclesService.updateVehicleByPlate(licensePlate, request));
    }

    /**
     * DELETE /api/vehicles/plate/{licensePlate} - Xóa xe theo biển số
     */
    @DeleteMapping("/plate/{licensePlate}")
    public ResponseEntity<Void> deleteByPlate(@PathVariable String licensePlate) {
        vehiclesService.deleteVehicleByPlate(licensePlate);
        return ResponseEntity.noContent().build();
    }

    /** PUT /api/vehicles/{id} - Cập nhật thông tin xe */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Integer id, @RequestBody VehicleDTO dto) {
        Vehicles v = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + id));
        if (dto.getLicensePlate() != null) v.setLicensePlate(dto.getLicensePlate());
        if (dto.getVehicleType() != null) v.setVehicleType(dto.getVehicleType());
        if (dto.getResidentId() != null) {
            Residents resident = residentRepository.findById(dto.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Resident not found: " + dto.getResidentId()));
            v.setResident(resident);
        }
        return ResponseEntity.ok(toDTO(vehicleRepository.save(v)));
    }
}
