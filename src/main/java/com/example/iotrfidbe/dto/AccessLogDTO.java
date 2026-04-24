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
public class AccessLogDTO {
    private Integer logId;
    private Integer residentId;
    private String residentName;
    private Integer vehicleId;
    private String vehiclePlate;
    private Integer cameraId;
    private String cameraName;
    private String detectedPlate;
    private Boolean faceMatch;
    private Boolean plateMatch;
    private String direction;
    private String imageFace;
    private String imagePlate;
    private LocalDateTime createdAt;
}
