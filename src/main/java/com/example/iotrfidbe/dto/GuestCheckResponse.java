package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response tra ve cho ca check-in va check-out cua khach van lai.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestCheckResponse {
    /** Co duoc phep vao/ra khong */
    private Boolean grantAccess;
    /** ID ban ghi khach trong database */
    private Integer guestId;
    /** The RFID da quet */
    private String cardUid;
    /** Bien so xe */
    private String licensePlate;
    /** Thong bao mo rong */
    private String message;
}
