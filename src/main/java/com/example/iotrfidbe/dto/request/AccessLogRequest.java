package com.example.iotrfidbe.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AccessLogRequest {

    // ===== OLD =====
    private Integer resident_id;
    private Integer vehicle_id;
    private String detected_plate;
    private Boolean face_match;
    private Boolean plate_match;
    private String direction;

    // ===== NEW =====
    private Boolean isCorrectFaceAndPlate;

    private String resident_name;

    private Integer enrollid;

    private List<String> db_plates;

    private String fail_reason;

    private String timestamp;
}