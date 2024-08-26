package org.example.domain.dto;

import org.example.domain.model.Bird;

import java.util.ArrayList;
import java.util.List;

public class BirdResponseDto {
    private List<Bird> birds = new ArrayList<>();

    public List<Bird> getBirds() {
        return birds;
    }

    public void setBirds(List<Bird> birds) {
        this.birds = birds;
    }
}
