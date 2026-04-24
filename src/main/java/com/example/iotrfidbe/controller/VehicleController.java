package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.VehicleDTO;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final ResidentRepository residentRepository;

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

    /** GET /api/vehicles - Lay danh sach tat ca xe */
    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAll() {
        return ResponseEntity.ok(vehicleRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList()));
    }

    /** GET /api/vehicles/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Integer id) {
        Vehicles v = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + id));
        return ResponseEntity.ok(toDTO(v));
    }

    /** GET /api/vehicles/resident/{residentId} - Lay xe cua cu dan */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<VehicleDTO>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(vehicleRepository.findByResident_ResidentId(residentId)
                .stream().map(this::toDTO).collect(Collectors.toList()));
    }

    /** POST /api/vehicles - Them xe moi */
    @PostMapping
    public ResponseEntity<VehicleDTO> create(@RequestBody VehicleDTO dto) {
        Vehicles v = new Vehicles();
        v.setLicensePlate(dto.getLicensePlate());
        v.setVehicleType(dto.getVehicleType());
        if (dto.getResidentId() != null) {
            Residents resident = residentRepository.findById(dto.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Resident not found: " + dto.getResidentId()));
            v.setResident(resident);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(vehicleRepository.save(v)));
    }

    /** PUT /api/vehicles/{id} - Cap nhat xe */
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

    /** DELETE /api/vehicles/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        vehicleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
