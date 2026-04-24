package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.Residents;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends JpaRepository<Residents, Integer> {

    List<Residents> findByFullNameContainingIgnoreCase(String name);

    List<Residents> findByStatus(String status);
}
