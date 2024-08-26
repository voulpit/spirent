package org.example.domain.dto;

import java.time.LocalDateTime;

public class UpdateSightingRequestDto extends CreateSightingRequestDto {
    private Long id;

    public UpdateSightingRequestDto(Long id, Long birdId, String location, LocalDateTime timestamp) {
        super(birdId, location, timestamp);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
