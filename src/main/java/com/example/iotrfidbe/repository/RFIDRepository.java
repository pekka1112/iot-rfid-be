package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.RFIDCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RFIDRepository
        extends JpaRepository<RFIDCard, Integer> {

    Optional<RFIDCard> findByCardUid(String cardUid);

    void deleteByCardUid(String cardUid);

}