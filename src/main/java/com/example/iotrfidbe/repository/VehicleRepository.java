package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.Vehicles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicles, Integer> {

    Optional<Vehicles> findByLicensePlate(String licensePlate);

    List<Vehicles> findByResident_ResidentId(Integer residentId);
}
