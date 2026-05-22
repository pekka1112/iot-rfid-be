package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiclesDTO {
    private Integer id;
    private String plateNumber;
    private String vehicleType;
    private Integer residentId;
    private String residentName;
}
