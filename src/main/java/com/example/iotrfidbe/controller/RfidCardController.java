package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.entity.RfidCard;
import com.example.iotrfidbe.service.RfidCardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rfid-cards")
@RequiredArgsConstructor
public class RfidCardController {

    private final RfidCardService service;

    @GetMapping
    public ResponseEntity<List<RfidCard>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RfidCard> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/uid/{cardUid}")
    public ResponseEntity<RfidCard> getByCardUid(@PathVariable String cardUid) {
        return ResponseEntity.ok(service.getByCardUid(cardUid));
    }

    @PostMapping
    public ResponseEntity<RfidCard> create(@RequestBody RfidCard rfidCard) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(rfidCard));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RfidCard> update(@PathVariable Integer id, @RequestBody RfidCard dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
