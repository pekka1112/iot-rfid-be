package com.example.iotrfidbe.repository.backupdata;

import com.example.iotrfidbe.entity.backupdata.UsersBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersBackupRepository extends JpaRepository<UsersBackup, Long> {
}