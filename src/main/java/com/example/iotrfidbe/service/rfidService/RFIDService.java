package com.example.iotrfidbe.service.rfidService;

import com.example.iotrfidbe.dto.response.RFIDResponse;
import com.example.iotrfidbe.entity.RfidCard;
import com.example.iotrfidbe.repository.RFIDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RFIDService {

    private final RFIDRepository rfidRepository;

    public RFIDResponse verify(
            String uid,
            String detectedPlate
    ) {

        Optional<RfidCard> optional =
                rfidRepository.findByCardUid(uid);

        // =========================
        // XE VÀO
        // =========================
        if(optional.isEmpty()) {

            RfidCard card = new RfidCard();

            card.setCardUid(uid);
            card.setPlateNumber(detectedPlate);
            card.setCreatedAt(LocalDateTime.now());

            rfidRepository.save(card);

            return RFIDResponse.builder()
                    .exists(false)
                    .matched(false)
                    .message("NEW CARD SAVED")
                    .detected_plate(detectedPlate)
                    .build();
        }

        // =========================
        // XE RA
        // =========================
        RfidCard card = optional.get();

        String dbPlate = card.getPlateNumber();

        boolean matched =
                dbPlate.equalsIgnoreCase(detectedPlate);

        // Nếu đúng -> xóa khỏi DB
        if(matched) {

            rfidRepository.delete(card);

            return RFIDResponse.builder()
                    .exists(true)
                    .matched(true)
                    .db_plate(dbPlate)
                    .detected_plate(detectedPlate)
                    .message("MATCHED -> OPEN GATE")
                    .build();
        }

        return RFIDResponse.builder()
                .exists(true)
                .matched(false)
                .db_plate(dbPlate)
                .detected_plate(detectedPlate)
                .message("PLATE NOT MATCH")
                .build();
    }
}