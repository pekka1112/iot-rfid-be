package com.example.iotrfidbe.service;

import com.example.iotrfidbe.entity.RfidCard;
import com.example.iotrfidbe.repository.RfidCardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RfidCardService {

    private final RfidCardRepository repository;

    public List<RfidCard> getAll() {
        return repository.findAll();
    }

    public RfidCard getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("RFID Card not found with id: " + id));
    }

    public RfidCard getByCardUid(String cardUid) {
        return repository.findByCardUid(cardUid).orElseThrow(() -> new RuntimeException("RFID Card not found with uid: " + cardUid));
    }

    @Transactional
    public RfidCard create(RfidCard rfidCard) {
        return repository.save(rfidCard);
    }

    @Transactional
    public RfidCard update(Integer id, RfidCard dto) {
        RfidCard existing = getById(id);
        if (dto.getCardUid() != null) {
            existing.setCardUid(dto.getCardUid());
        }
        if (dto.getPlateNumber() != null) {
            existing.setPlateNumber(dto.getPlateNumber());
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
