package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.DeviceBackups;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceBackupRepository extends JpaRepository<DeviceBackups, Integer> {

    /**
     * Tim kiem device backup theo device_user_id (ID nguoi dung tren may FaceID/van tay).
     * Day la key chinh de map tu tin hieu tu thiet bi sang resident_id.
     */
    Optional<DeviceBackups> findByDeviceUserId(Integer deviceUserId);

    List<DeviceBackups> findByResident_ResidentId(Integer residentId);
}
