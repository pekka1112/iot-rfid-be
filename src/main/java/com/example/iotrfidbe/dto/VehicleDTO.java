package com.example.iotrfidbe.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Integer vehicleId;
    private Integer residentId;
    private String residentName;
    private String licensePlate;
    private String vehicleType;
    private LocalDateTime createdAt;
}
