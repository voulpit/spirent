package org.example.controller;

import org.example.domain.dto.CreateSightingRequestDto;
import org.example.domain.dto.SightingResponseDto;
import org.example.domain.dto.UpdateSightingRequestDto;
import org.example.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/sightings")
public class SightingController {
    @Autowired
    private SightingService sightingService;

    @GetMapping("/all")
    public ResponseEntity<SightingResponseDto> getAllSightings() {
        return ResponseEntity.ok(sightingService.getSightings());
    }

    @GetMapping
    public ResponseEntity<SightingResponseDto> getSightings(@RequestParam(required = false) Long birdId,
                                                            @RequestParam(required = false) String location,
                                                            @RequestParam(required = false) String from,
                                                            @RequestParam(required = false) String until) {
        LocalDateTime dateTimeFrom = from != null ? LocalDateTime.parse(from) : null;
        LocalDateTime dateTimeUntil = until != null ? LocalDateTime.parse(until) : null;
        return ResponseEntity.ok(sightingService.getSightings(birdId, location, dateTimeFrom, dateTimeUntil));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SightingResponseDto> getSighting(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sightingService.getSighting(id));
    }

    @PostMapping("/new")
    public ResponseEntity<SightingResponseDto> createSighting(@RequestBody CreateSightingRequestDto requestDto) {
        return ResponseEntity.ok(sightingService.createSighting(requestDto.getBirdId(), requestDto.getLocation(),
                requestDto.getTimestamp()));
    }

    @PutMapping
    public ResponseEntity<SightingResponseDto> updateSighting(@RequestBody UpdateSightingRequestDto requestDto) {
        return ResponseEntity.ok(sightingService.updateSighting(requestDto.getId(), requestDto.getBirdId(),
                requestDto.getLocation(), requestDto.getTimestamp()));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteSighting(@RequestParam Long id) {
        sightingService.deleteSighting(id);
        return ResponseEntity.ok(true);
    }
}
