package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request khi khach van lai quet the RFID luc vao.
 * The RFID da chua san bien so xe (stored on card).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestCheckinRequest {
    /** UID the RFID phat cho khach */
    private String cardUid;
    /** Bien so xe khach - doc tu the hoac camera */
    private String licensePlate;
    /** ID camera / cong luc vao */
    private Integer gateInId;
}
