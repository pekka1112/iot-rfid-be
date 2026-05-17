package com.example.iotrfidbe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "rfid_cards")
@Data
public class RFIDCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rfid_id")
    private Integer rfidId;

    @Column(name = "card_uid", unique = true)
    private String cardUid;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}