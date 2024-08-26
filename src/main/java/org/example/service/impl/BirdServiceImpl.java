package org.example.service.impl;

import org.example.domain.dto.BirdResponseDto;
import org.example.domain.exception.BirdValidationException;
import org.example.infra.entity.Bird;
import org.example.infra.mapper.BirdMapper;
import org.example.infra.repo.BirdRepository;
import org.example.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.util.Constants.BIRD_COLOR_MAX_LEN;
import static org.example.util.Constants.BIRD_NAME_MAX_LEN;

@Service
@Transactional
public class BirdServiceImpl implements BirdService {
    @Autowired
    private BirdRepository birdRepository;

    @Override
    public BirdResponseDto getBirds() {
        BirdResponseDto result = new BirdResponseDto();
        List<Bird> birds = birdRepository.findAll();
        birds.forEach(bird -> result.getBirds().add(BirdMapper.entityToModel(bird)));
        return result;
    }

    @Override
    public BirdResponseDto getBirds(String name, String color) {
        if (name == null && color == null) {
            return getBirds();
        }
        BirdResponseDto result = new BirdResponseDto();
        List<Bird> birds;
        if (name != null && color != null) {
            birds = birdRepository.findAllByNameAndColor(name, color);
        } else if (name != null) {
            birds = birdRepository.findAllByName(name);
        } else {
            birds = birdRepository.findAllByColor(color);
        }
        birds.forEach(bird -> result.getBirds().add(BirdMapper.entityToModel(bird)));
        return result;
    }

    @Override
    public BirdResponseDto createBird(String name, String color, Double weight, Double height) {
        validateInput(name, color, weight, height);

        Bird bird = new Bird(name, color, weight, height);
        bird = birdRepository.save(bird);

        BirdResponseDto responseDto = new BirdResponseDto();
        responseDto.setBirds(new ArrayList<>());
        responseDto.getBirds().add(BirdMapper.entityToModel(bird));
        return responseDto;
    }

    @Override
    public BirdResponseDto updateBird(Long id, String name, String color, Double weight, Double height) {
        checkBirdId(id);
        validateInput(name, color, weight, height);

        Optional<Bird> birdOptional = birdRepository.findById(id);
        if (birdOptional.isEmpty()) {
            throw new BirdValidationException("Invalid bird id!");
        }

        Bird bird = birdOptional.get();
        bird.setName(name);
        bird.setColor(color);
        bird.setWeight(weight);
        bird.setHeight(height);
        bird = birdRepository.save(bird);

        BirdResponseDto responseDto = new BirdResponseDto();
        responseDto.getBirds().add(BirdMapper.entityToModel(bird));
        return responseDto;
    }

    @Override
    public void deleteBird(Long id) {
        checkBirdId(id);
        Optional<Bird> birdOptional = birdRepository.findById(id);
        if (birdOptional.isEmpty()) {
            throw new BirdValidationException("Invalid bird id!");
        }

        Bird bird = birdOptional.get();
        birdRepository.delete(bird);
    }

    private void checkBirdId(Long id) {
        if (id == null) {
            throw new BirdValidationException("Bird id not provided!");
        }
    }

    private void validateInput(String name, String color, Double weight, Double height) {
        checkBirdName(name);
        checkBirdColor(color);
        checkBirdWeight(weight);
        checkBirdHeight(height);
    }

    private void checkBirdName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BirdValidationException("Bird name not provided!");
        }
        if (name.length() > BIRD_NAME_MAX_LEN) {
            throw new BirdValidationException("Bird name exceeds max length!");
        }
    }

    private void checkBirdColor(String color) {
        if (color == null || color.isEmpty()) {
            throw new BirdValidationException("Bird color not provided!");
        }
        if (color.length() > BIRD_COLOR_MAX_LEN) {
            throw new BirdValidationException("Bird color exceeds max length!");
        }
    }

    private void checkBirdWeight(Double weight) {
        if (weight != null && weight <= 0) {
            throw new BirdValidationException("Invalid weight provided!");
        }
    }

    private void checkBirdHeight(Double height) {
        if (height != null && height <= 0) {
            throw new BirdValidationException("Invalid height provided!");
        }
    }
}
