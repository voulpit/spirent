package org.example.service.impl;

import org.example.domain.dto.SightingResponseDto;
import org.example.domain.exception.SightingValidationException;
import org.example.infra.entity.Bird;
import org.example.infra.entity.Sighting;
import org.example.infra.mapper.SightingMapper;
import org.example.infra.repo.BirdRepository;
import org.example.infra.repo.SightingRepository;
import org.example.service.SightingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.util.Constants.SIGHTING_LOCATION_MAX_LEN;

@Service
@Transactional
public class SightingServiceImpl implements SightingService {
    @Autowired
    private SightingRepository sightingRepository;

    @Autowired
    private BirdRepository birdRepository;

    @Override
    public SightingResponseDto getSightings() {
        SightingResponseDto responseDto = new SightingResponseDto();
        List<Sighting> sightings = sightingRepository.findAll();
        sightings.forEach(sighting -> responseDto.getSightings().add(SightingMapper.entityToModel(sighting)));
        return responseDto;
    }

    @Override
    public SightingResponseDto getSightings(Long birdId, String location, LocalDateTime from, LocalDateTime until) {
        if (birdId == null && location == null && from == null && until == null) {
            return getSightings();
        }
        SightingResponseDto responseDto = new SightingResponseDto();
        List<Sighting> sightings = sightingRepository.findAllByBirdIdAndLocationAndTimeFrame(birdId, location, from, until);
        sightings.forEach(sighting -> responseDto.getSightings().add(SightingMapper.entityToModel(sighting)));
        return responseDto;
    }

    @Override
    public SightingResponseDto getSighting(Long id) {
        checkSightingId(id);
        Optional<Sighting> sightingOptional = sightingRepository.findById(id);
        if (sightingOptional.isEmpty()) {
            throw new NoSuchElementException("Invalid sighting id!");
        }
        SightingResponseDto responseDto = new SightingResponseDto();
        responseDto.getSightings().add(SightingMapper.entityToModel(sightingOptional.get()));
        return responseDto;
    }

    @Override
    public SightingResponseDto createSighting(Long birdId, String location, LocalDateTime timestamp) {
        checkBirdId(birdId);
        checkLocation(location);

        Optional<Bird> birdOptional = birdRepository.findById(birdId);
        if (birdOptional.isEmpty()) {
            throw new SightingValidationException("Bird ID is not valid!");
        }

        Sighting sighting = new Sighting();
        sighting.setBird(birdOptional.get());
        sighting.setLocation(location);
        sighting.setTimestamp(timestamp);
        sighting = sightingRepository.save(sighting);

        SightingResponseDto responseDto = new SightingResponseDto();
        responseDto.getSightings().add(SightingMapper.entityToModel(sighting));
        return responseDto;
    }

    @Override
    public SightingResponseDto updateSighting(Long id, Long birdId, String location, LocalDateTime timestamp) {
        checkSightingId(id);
        checkBirdId(birdId);
        checkLocation(location);

        Optional<org.example.infra.entity.Sighting> sightingOptional = sightingRepository.findById(id);
        if (sightingOptional.isEmpty()) {
            throw new SightingValidationException("Invalid sighting id!");
        }

        Sighting sighting = sightingOptional.get();
        if (!birdId.equals(sighting.getBird().getId())) {
            Optional<Bird> birdOptional = birdRepository.findById(birdId);
            if (birdOptional.isEmpty()) {
                throw new SightingValidationException("Bird id is not valid!");
            }
            sighting.setBird(birdOptional.get());
        }
        sighting.setLocation(location);
        sighting.setTimestamp(timestamp);
        sighting = sightingRepository.save(sighting);

        SightingResponseDto responseDto = new SightingResponseDto();
        responseDto.getSightings().add(SightingMapper.entityToModel(sighting));
        return responseDto;
    }

    @Override
    public void deleteSighting(Long id) {
        if (id == null) {
            throw new SightingValidationException("Sighting id not provided!");
        }
        Optional<org.example.infra.entity.Sighting> sightingOptional = sightingRepository.findById(id);
        if (sightingOptional.isEmpty()) {
            throw new SightingValidationException("Invalid sighting id!");
        }

        Sighting sighting = sightingOptional.get();
        sightingRepository.delete(sighting);
    }

    private void checkSightingId(Long id) {
        if (id == null) {
            throw new SightingValidationException("Sighting id not provided!");
        }
        if (id < 0) {
            throw new SightingValidationException("Invalid sighting id!");
        }
    }

    private void checkBirdId(Long id) {
        if (id == null) {
            throw new SightingValidationException("Bird ID not provided!");
        }
    }

    private void checkLocation(String location) {
        if (location == null || location.isEmpty()) {
            throw new SightingValidationException("Location not provided!");
        }
        if (location.length() > SIGHTING_LOCATION_MAX_LEN) {
            throw new SightingValidationException("Location exceeds max length!");
        }
    }
}
