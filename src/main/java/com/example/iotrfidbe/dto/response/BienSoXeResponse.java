package com.example.iotrfidbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BienSoXeResponse {
    private Integer residentId;
    private Integer vehicleId;
    private String plateNumber;
}