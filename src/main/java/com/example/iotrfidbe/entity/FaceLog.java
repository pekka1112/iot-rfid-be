package com.example.iotrfidbe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "face_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaceLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "face_id")
    private Integer faceId;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}