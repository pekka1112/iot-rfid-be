package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.ResidentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResidentDetailRepository extends JpaRepository<ResidentDetail, Integer> {

    @Query("SELECT r FROM ResidentDetail r LEFT JOIN FETCH r.vehicles")
    List<ResidentDetail> findAllWithVehicles();

    @Query("SELECT r FROM ResidentDetail r LEFT JOIN FETCH r.vehicles WHERE r.residentId = :id")
    Optional<ResidentDetail> findByIdWithVehicles(@Param("id") Integer id);
}
