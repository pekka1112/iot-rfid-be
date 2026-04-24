package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.entity.RfidCards;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.entity.Vehicles;
import com.example.iotrfidbe.repository.RfidCardRepository;
import com.example.iotrfidbe.repository.ResidentRepository;
import com.example.iotrfidbe.repository.VehicleRepository;
import java.util.List;
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
@RequestMapping("/api/rfid-cards")
@RequiredArgsConstructor
public class RfidCardController {

    private final RfidCardRepository rfidCardRepository;
    private final ResidentRepository residentRepository;
    private final VehicleRepository vehicleRepository;

    /** GET /api/rfid-cards */
    @GetMapping
    public ResponseEntity<List<RfidCards>> getAll() {
        return ResponseEntity.ok(rfidCardRepository.findAll());
    }

    /** GET /api/rfid-cards/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<RfidCards> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(rfidCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RfidCard not found: " + id)));
    }

    /** GET /api/rfid-cards/resident/{residentId} */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<RfidCards>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(rfidCardRepository.findByResident_ResidentId(residentId));
    }

    /** POST /api/rfid-cards - Cap phat the RFID moi */
    @PostMapping
    public ResponseEntity<RfidCards> create(@RequestBody RfidCardRequest request) {
        RfidCards card = new RfidCards();
        card.setCardUid(request.getCardUid());
        card.setIssuedDate(request.getIssuedDate());
        card.setExpireDate(request.getExpireDate());

        if (request.getResidentId() != null) {
            Residents resident = residentRepository.findById(request.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Resident not found: " + request.getResidentId()));
            card.setResident(resident);
        }
        if (request.getVehicleId() != null) {
            Vehicles vehicle = vehicleRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found: " + request.getVehicleId()));
            card.setVehicle(vehicle);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rfidCardRepository.save(card));
    }

    /** PUT /api/rfid-cards/{id} */
    @PutMapping("/{id}")
    public ResponseEntity<RfidCards> update(@PathVariable Integer id, @RequestBody RfidCardRequest request) {
        RfidCards card = rfidCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RfidCard not found: " + id));
        if (request.getCardUid() != null) card.setCardUid(request.getCardUid());
        if (request.getIssuedDate() != null) card.setIssuedDate(request.getIssuedDate());
        if (request.getExpireDate() != null) card.setExpireDate(request.getExpireDate());
        if (request.getResidentId() != null) {
            Residents resident = residentRepository.findById(request.getResidentId())
                    .orElseThrow(() -> new RuntimeException("Resident not found"));
            card.setResident(resident);
        }
        if (request.getVehicleId() != null) {
            Vehicles vehicle = vehicleRepository.findById(request.getVehicleId())
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            card.setVehicle(vehicle);
        }
        return ResponseEntity.ok(rfidCardRepository.save(card));
    }

    /** DELETE /api/rfid-cards/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rfidCardRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Inner request class (tranh tao them file DTO cho entity don gian) ----
    @lombok.Data
    public static class RfidCardRequest {
        private String cardUid;
        private Integer residentId;
        private Integer vehicleId;
        private java.time.LocalDateTime issuedDate;
        private java.time.LocalDateTime expireDate;
    }
}
