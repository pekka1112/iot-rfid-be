package com.example.iotrfidbe.entity;

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
public class RfidUsersBackup {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "image_path")
    private String imagePath;
}
