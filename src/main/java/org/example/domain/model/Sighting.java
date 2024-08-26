package org.example.domain.model;

import java.time.LocalDateTime;

public class Sighting {
    private Long id;
    private Bird bird;
    private String location;
    private LocalDateTime timestamp;

    public Sighting() {
    }

    public Sighting(Bird bird, String location, LocalDateTime timestamp) {
        this.bird = bird;
        this.location = location;
        this.timestamp = timestamp;
    }

    public Sighting(Long id, Bird bird, String location, LocalDateTime timestamp) {
        this.id = id;
        this.bird = bird;
        this.location = location;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bird getBird() {
        return bird;
    }

    public void setBird(Bird bird) {
        this.bird = bird;
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

    public void setTimestamp(LocalDateTime dateTimeFrom) {
        this.timestamp = timestamp;
    }
}
