package org.example.domain.dto;

import java.time.LocalDateTime;

public class CreateSightingRequestDto {
    private Long birdId;
    private String location;
    private LocalDateTime timestamp;

    public CreateSightingRequestDto() {
    }

    public CreateSightingRequestDto(Long birdId, String location, LocalDateTime timestamp) {
        this.birdId = birdId;
        this.location = location;
        this.timestamp = timestamp;
    }

    public Long getBirdId() {
        return birdId;
    }

    public void setBirdId(Long birdId) {
        this.birdId = birdId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
