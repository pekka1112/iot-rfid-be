package com.example.iotrfidbe.dto.backup;

import lombok.Data;

@Data
public class UsersBackupDTO {
    private Long id;
    private String name;
    private String base64;
}