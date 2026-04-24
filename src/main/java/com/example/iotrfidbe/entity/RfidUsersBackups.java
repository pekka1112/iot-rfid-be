package com.example.iotrfidbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bảng lưu thông tin khách vãn lai vào/ra bằng thẻ RFID tạm thời.
 * status: 'in_building' (đang ở trong), 'departed' (đã ra)
 */
@Entity
@Table(
        name = "rfid_users_backups",
        indexes = {
                @Index(name = "idx_guest_card", columnList = "card_uid"),
                @Index(name = "idx_guest_plate", columnList = "license_plate")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RfidUsersBackups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Integer guestId;

    /** UID thẻ RFID tạm thời phát cho khách */
    @Column(name = "card_uid", nullable = false, length = 50)
    private String cardUid;

    /** Biển số xe khách */
    @Column(name = "license_plate", nullable = false, length = 20)
    private String licensePlate;

    @Column(name = "check_in_at")
    private LocalDateTime checkInAt;

    @Column(name = "check_out_at")
    private LocalDateTime checkOutAt;

    /** 'in_building' hoặc 'departed' */
    @Column(name = "status", length = 20)
    private String status = "in_building";

    /** ID camera/cổng lúc vào */
    @Column(name = "gate_in_id")
    private Integer gateInId;

    /** ID camera/cổng lúc ra */
    @Column(name = "gate_out_id")
    private Integer gateOutId;

    @PrePersist
    protected void onCreate() {
        if (checkInAt == null) {
            checkInAt = LocalDateTime.now();
        }
        if (status == null) {
            status = "in_building";
        }
    }
}
