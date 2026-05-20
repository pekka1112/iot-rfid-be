package com.example.iotrfidbe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "residents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentDetail {

    @Id
    @Column(name = "resident_id")
    private Integer residentId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status", length = 20)
    private String status = "active";

    // Quan hệ 1-nhiều với Vehicles
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "resident_id")
    private java.util.List<Vehicles> vehicles = new java.util.ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}