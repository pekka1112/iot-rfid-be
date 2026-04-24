package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.FireAlerts;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireAlertRepository extends JpaRepository<FireAlerts, Integer> {

    List<FireAlerts> findByStatus(String status);

    List<FireAlerts> findAllByOrderByCreatedAtDesc();
}
