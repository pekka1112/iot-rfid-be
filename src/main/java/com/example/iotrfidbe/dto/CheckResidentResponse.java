package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response tra ve sau khi kiem tra cu dan qua FaceID + bien so xe.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckResidentResponse {
    /** Co phai cu dan hop le trong database khong */
    private Boolean isResident;
    private Integer residentId;
    private String fullName;
    /** Co duoc phep mo cua (face + bien so khop nhau) */
    private Boolean grantAccess;
    private String message;
}
