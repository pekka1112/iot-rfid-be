package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RfidUsersBackups;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RfidUsersBackupRepository extends JpaRepository<RfidUsersBackups, Integer> {

    /**
     * Tim kiem record check-in cua mot khach van lai dang con o trong toa nha.
     * Lay ban ghi moi nhat cua the RFID co status = 'in_building'.
     */
    Optional<RfidUsersBackups> findTopByCardUidAndStatusOrderByCheckInAtDesc(
            String cardUid, String status);

    List<RfidUsersBackups> findByStatus(String status);

    List<RfidUsersBackups> findByLicensePlate(String licensePlate);

    List<RfidUsersBackups> findAllByOrderByCheckInAtDesc();
}
