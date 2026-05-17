package com.example.iotrfidbe.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RfidVerifyResponse { // trả về 3 tt này cho python

    private Integer resident_id;
    private Integer vehicle_id;
    private String plate_number;

}