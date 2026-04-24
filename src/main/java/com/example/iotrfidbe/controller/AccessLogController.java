package com.example.iotrfidbe.controller;

import com.example.iotrfidbe.dto.AccessLogDTO;
import com.example.iotrfidbe.service.AccessLogService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // mark return JSON Data, not HTML
@RequestMapping("/api/access-logs") // prefix của tất cả API class
@RequiredArgsConstructor // lombok lib
public class AccessLogController {

    private final AccessLogService accessLogService; // Inject Service - process all logic in service, not controller

    /**
     * call to service to impl method get all access log from DB
     * @return: DTO filter by AccessLog
     */
    @GetMapping
    public ResponseEntity<List<AccessLogDTO>> getAll() {
        return ResponseEntity.ok(accessLogService.getAll());
    }

    /**
     * get accessLog by logId
     * @param id of accesslog
     * @return DTO filter by AccessLog
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccessLogDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(accessLogService.getById(id));
    }

    /**
     * get resident by resident Id
     * @param residentId
     * @return DTO filter by AccessLog
     */
    @GetMapping("/resident/{residentId}")
    public ResponseEntity<List<AccessLogDTO>> getByResident(@PathVariable Integer residentId) {
        return ResponseEntity.ok(accessLogService.getByResident(residentId));
    }

    /**
     * GET /api/access-logs/range?from=2024-01-01T00:00:00&to=2024-12-31T23:59:59
     * Loc nhat ky theo khoang thoi gian
     */
    @GetMapping("/range")
    public ResponseEntity<List<AccessLogDTO>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return ResponseEntity.ok(accessLogService.getByDateRange(from, to));
    }

    /** POST /api/access-logs - Tao moi nhat ky (thu cong hoac tu he thong camera) */
    @PostMapping
    public ResponseEntity<AccessLogDTO> create(@RequestBody AccessLogDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accessLogService.create(dto));
    }

    /** DELETE /api/access-logs/{id} - Xoa nhat ky */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        accessLogService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
