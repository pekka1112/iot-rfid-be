package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.Cameras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Cameras, Integer> {
}
