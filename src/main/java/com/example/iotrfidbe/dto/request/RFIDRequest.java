package com.example.iotrfidbe.dto.request;
import lombok.Data;

@Data
public class RFIDRequest {
    private String card_uid;
    private String detected_plate;
}