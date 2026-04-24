package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.AccessLogs;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogs, Integer> {

    List<AccessLogs> findByResident_ResidentIdOrderByCreatedAtDesc(Integer residentId);

    List<AccessLogs> findAllByOrderByCreatedAtDesc();

    List<AccessLogs> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime from, LocalDateTime to);

    List<AccessLogs> findByDirection(String direction);
}
