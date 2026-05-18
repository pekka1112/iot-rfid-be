package com.example.iotrfidbe.dto.request;
import lombok.Data;

@Data
public class AccessLogRequest {
    private Integer resident_id;
    private Integer vehicle_id;
    private String detected_plate;
    private Boolean face_match;
    private Boolean plate_match;
    private String direction;
}
