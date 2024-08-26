package org.example.domain.dto;

public class UpdateBirdRequestDto extends CreateBirdRequestDto {
    private Long id;

    public UpdateBirdRequestDto(Long id, String name, String color, Double weight, Double height) {
        super(name, color, weight, height);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
