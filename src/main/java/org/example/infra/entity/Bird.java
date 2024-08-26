package org.example.infra.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "Bird")
public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "WEIGHT")
    private Double weight;

    @Column(name = "HEIGHT")
    private Double height;

    @OneToMany(mappedBy = "bird")
    private List<Sighting> sightings;

    public Bird() {
    }

    public Bird(String name, String color, Double weight, Double height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public Bird(Long id, String name, String color, Double weight, Double height) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }
}
