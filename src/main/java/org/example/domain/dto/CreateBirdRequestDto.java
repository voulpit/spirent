package org.example.domain.dto;

public class CreateBirdRequestDto {
    private String name;
    private String color;
    private Double weight;
    private Double height;

    public CreateBirdRequestDto() {
    }

    public CreateBirdRequestDto(String name, String color, Double weight, Double height) {
        this.name = name;
        this.color = color;
        this.weight = weight;
        this.height = height;
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
}
