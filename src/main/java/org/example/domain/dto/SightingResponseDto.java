package org.example.domain.dto;

import org.example.domain.model.Sighting;

import java.util.ArrayList;
import java.util.List;

public class SightingResponseDto {
    private List<Sighting> sightings = new ArrayList<>();

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }
}
