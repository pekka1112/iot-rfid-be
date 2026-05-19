package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RfidCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RfidCardRepository extends JpaRepository<RfidCard, Integer> {
    Optional<RfidCard> findByCardUid(String cardUid);
}