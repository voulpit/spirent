package org.example.controller;

import org.example.domain.dto.BirdResponseDto;
import org.example.domain.dto.CreateBirdRequestDto;
import org.example.domain.dto.UpdateBirdRequestDto;
import org.example.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/birds")
public class BirdController {
    @Autowired
    private BirdService birdService;

    @GetMapping("/all")
    public ResponseEntity<BirdResponseDto> getAllBirds() {
        return ResponseEntity.ok(birdService.getBirds());
    }

    @GetMapping
    public ResponseEntity<BirdResponseDto> getBirds(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String color) {
        return ResponseEntity.ok(birdService.getBirds(name, color));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BirdResponseDto> getBird(@PathVariable("id") Long id) {
        return ResponseEntity.ok(birdService.getBird(id));
    }

    @PostMapping("/new")
    public ResponseEntity<BirdResponseDto> createBird(@RequestBody CreateBirdRequestDto requestDto) {
        return ResponseEntity.ok(birdService.createBird(requestDto.getName(), requestDto.getColor(),
                requestDto.getWeight(), requestDto.getHeight()));
    }

    @PutMapping
    public ResponseEntity<BirdResponseDto> updateBird(@RequestBody UpdateBirdRequestDto requestDto) {
        return ResponseEntity.ok(birdService.updateBird(requestDto.getId(), requestDto.getName(),
                requestDto.getColor(), requestDto.getWeight(), requestDto.getHeight()));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteBird(@RequestParam Long id) {
        birdService.deleteBird(id);
        return ResponseEntity.ok(true);
    }
}
