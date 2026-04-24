package com.example.iotrfidbe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request payload khi thiet bi nhan dien gui ve de kiem tra cu dan.
 *
 * deviceUserId: ID nguoi dung tren may nhan dien (FaceID / Van tay).
 *               Day la gia tri ma may Access Control tra ve sau khi nhan dien thanh cong.
 *               Backend se tra cuu bang device_backups de lay resident_id tuong ung.
 *
 * licensePlate: Bien so xe doc duoc tu camera bien so.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckResidentRequest {
    private Integer deviceUserId;
    private String licensePlate;
}
