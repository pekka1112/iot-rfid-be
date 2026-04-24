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
public class ResidentDTO {
    private Integer residentId;
    private String fullName;
    private String phone;
    private Integer birthYear;
    private String status;
    private LocalDateTime createdAt;
}
