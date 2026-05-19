package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RfidUsersBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RfidUsersBackupRepository extends JpaRepository<RfidUsersBackup, Integer> {

}
