package com.example.iotrfidbe.entity.backupdata;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rfid_users_backups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersBackup {
    @Id
    private Long id;
    private String name;
    @Column(columnDefinition = "LONGTEXT")
    private String image_path;
}