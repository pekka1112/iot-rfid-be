package com.example.iotrfidbe.dto.request;

import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResidentDetailRequest {

    private Integer residentId;
    private String  fullName;
    private String  phone;
    private Integer birthYear;
    private String  status;
    // Danh sách biển số xe kèm loại xe
    private List<VehicleRequest> vehicles;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VehicleRequest {
        private String licensePlate;
        private String vehicleType;
    }
}