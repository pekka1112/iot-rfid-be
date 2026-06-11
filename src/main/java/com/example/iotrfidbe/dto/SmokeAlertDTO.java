package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmokeAlertDTO {
    private String status;      // ALERT hoặc CLEAR
    private String timestamp;
}