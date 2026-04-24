package com.example.iotrfidbe.service;

import com.example.iotrfidbe.dto.ResidentDTO;
import com.example.iotrfidbe.entity.Residents;
import com.example.iotrfidbe.repository.ResidentRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResidentService {

    private final ResidentRepository residentRepository;

    // ==================== Mapper ====================

    private ResidentDTO toDTO(Residents r) {
        return ResidentDTO.builder()
                .residentId(r.getResidentId())
                .fullName(r.getFullName())
                .phone(r.getPhone())
                .birthYear(r.getBirthYear())
                .status(r.getStatus())
                .createdAt(r.getCreatedAt())
                .build();
    }

    private Residents toEntity(ResidentDTO dto) {
        Residents r = new Residents();
        r.setFullName(dto.getFullName());
        r.setPhone(dto.getPhone());
        r.setBirthYear(dto.getBirthYear());
        r.setStatus(dto.getStatus() != null ? dto.getStatus() : "active");
        return r;
    }

    // ==================== CRUD ====================

    public List<ResidentDTO> getAll() {
        return residentRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ResidentDTO getById(Integer id) {
        Residents r = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found: " + id));
        return toDTO(r);
    }

    @Transactional
    public ResidentDTO create(ResidentDTO dto) {
        Residents saved = residentRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Transactional
    public ResidentDTO update(Integer id, ResidentDTO dto) {
        Residents r = residentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resident not found: " + id));
        if (dto.getFullName() != null) r.setFullName(dto.getFullName());
        if (dto.getPhone() != null) r.setPhone(dto.getPhone());
        if (dto.getBirthYear() != null) r.setBirthYear(dto.getBirthYear());
        if (dto.getStatus() != null) r.setStatus(dto.getStatus());
        return toDTO(residentRepository.save(r));
    }

    @Transactional
    public void delete(Integer id) {
        if (!residentRepository.existsById(id)) {
            throw new RuntimeException("Resident not found: " + id);
        }
        residentRepository.deleteById(id);
    }

    public List<ResidentDTO> searchByName(String name) {
        return residentRepository.findByFullNameContainingIgnoreCase(name)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ResidentDTO> getByStatus(String status) {
        return residentRepository.findByStatus(status)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
