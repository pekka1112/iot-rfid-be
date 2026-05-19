package com.example.iotrfidbe.service;

import com.example.iotrfidbe.entity.RfidUsersBackup;
import com.example.iotrfidbe.repository.RfidUsersBackupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RfidUsersBackupService {

    private final RfidUsersBackupRepository repository;

    public List<RfidUsersBackup> getAll() {
        return repository.findAll();
    }

    public RfidUsersBackup getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User backup not found with id: " + id));
    }

    @Transactional
    public RfidUsersBackup create(RfidUsersBackup rfidUsersBackup) {
        return repository.save(rfidUsersBackup);
    }

    @Transactional
    public RfidUsersBackup update(Integer id, RfidUsersBackup dto) {
        RfidUsersBackup existing = getById(id);
        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getImagePath() != null) {
            existing.setImagePath(dto.getImagePath());
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
