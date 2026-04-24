package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RfidCards;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RfidCardRepository extends JpaRepository<RfidCards, Integer> {

    Optional<RfidCards> findByCardUid(String cardUid);

    List<RfidCards> findByResident_ResidentId(Integer residentId);

    List<RfidCards> findByVehicle_VehicleId(Integer vehicleId);
}
