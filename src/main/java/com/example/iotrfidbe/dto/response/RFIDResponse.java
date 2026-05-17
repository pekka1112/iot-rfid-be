package com.example.iotrfidbe.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RFIDResponse {

    private boolean exists;
    private boolean matched;
    private String db_plate;
    private String detected_plate;
    private String message;
}
