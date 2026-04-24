package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request khi khach van lai quet the RFID luc ra.
 * He thong kiem tra: the RFID + bien so xe co khop voi record check-in khong.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestCheckoutRequest {
    /** UID the RFID khach qua quet luc ra */
    private String cardUid;
    /** Bien so xe khach - doc tu the hoac camera luc ra */
    private String licensePlate;
    /** ID camera / cong luc ra */
    private Integer gateOutId;
}
