package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.Faces;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaceRepository extends JpaRepository<Faces, Integer> {

    List<Faces> findByResident_ResidentId(Integer residentId);
}
