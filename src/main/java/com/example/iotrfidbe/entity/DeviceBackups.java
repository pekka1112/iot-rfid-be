package com.example.iotrfidbe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Bảng lưu thông tin người dùng trên thiết bị kiểm soát ra vào
 * (FaceID, vân tay, thẻ...). Dùng để tra cứu resident_id khi
 * thiết bị trả về device_user_id sau khi nhận diện.
 */
@Entity
@Table(
        name = "device_backups",
        indexes = {
                @Index(name = "idx_device_user", columnList = "device_user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceBackups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backup_id")
    private Integer backupId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id")
    private Residents resident;

    /** ID nội bộ trên thiết bị nhận dạng (FaceID/vân tay) */
    @Column(name = "device_user_id", nullable = false)
    private Integer deviceUserId;

    /** Tên người dùng hiển thị trên thiết bị */
    @Column(name = "user_name_on_device", length = 50)
    private String userNameOnDevice;

    /** Loại credential: FaceID, Fingerprint, Card */
    @Column(name = "credential_type", length = 50)
    private String credentialType;

    /** Số Backup trên thiết bị */
    @Column(name = "backup_num", nullable = false)
    private Integer backupNum;

    /** Quyền Admin trên máy: 1 = có, 0 = không */
    @Column(name = "is_admin")
    private Integer isAdmin;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        updatedAt = LocalDateTime.now();
    }
}
