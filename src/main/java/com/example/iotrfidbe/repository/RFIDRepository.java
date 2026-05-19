package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RfidCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RFIDRepository
        extends JpaRepository<RfidCard, Integer> {

    Optional<RfidCard> findByCardUid(String cardUid);

    void deleteByCardUid(String cardUid);

}