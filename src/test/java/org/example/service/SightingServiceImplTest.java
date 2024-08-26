package org.example.service;

import org.example.domain.dto.SightingResponseDto;
import org.example.domain.exception.SightingValidationException;
import org.example.infra.entity.Bird;
import org.example.infra.entity.Sighting;
import org.example.infra.repo.BirdRepository;
import org.example.infra.repo.SightingRepository;
import org.example.service.impl.SightingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.util.Constants.SIGHTING_LOCATION_MAX_LEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SightingServiceImplTest {
    @Mock
    private SightingRepository sightingRepository;
    @Mock
    private BirdRepository birdRepository;
    @InjectMocks
    private SightingServiceImpl sightingService;

    private final LocalDateTime sampleLocalDatetime = LocalDateTime.parse("2024-08-20T08:00:01.000000");

    @Test
    public void testGetBirdAllSightings_callsRepo() {
        // when
        sightingService.getSightings();
        // then
        verify(sightingRepository, times(1)).findAll();
    }

    @Test
    public void testGetBirdAllSightings_withResults() {
        // given
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(new Sighting());
        sightings.add(new Sighting(1L, new Bird(), "RO", null));
        doReturn(sightings).when(sightingRepository).findAll();
        // when
        SightingResponseDto responseDto = sightingService.getSightings();
        // then
        assertNotNull(responseDto);
        assertEquals(2, responseDto.getSightings().size());
        assertEquals("RO", responseDto.getSightings().get(1).getLocation());
    }

    @Test
    public void testGetSightings_filtered_callsRepo() {
        // when
        sightingService.getSightings(null, null, null, null);
        // then
        verify(sightingRepository, times(1)).findAll();

        // when
        sightingService.getSightings(1L, "Romania", sampleLocalDatetime, sampleLocalDatetime);
        // then
        verify(sightingRepository, times(1)).findAllByBirdIdAndLocationAndTimeFrame(1L, "Romania", sampleLocalDatetime, sampleLocalDatetime);
    }

    @Test
    public void testGetSightings_filtered_withResults() {
        // given
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(new Sighting(1L, new Bird(), "RO", sampleLocalDatetime));
        sightings.add(new Sighting(2L, new Bird(), "RO", null));
        doReturn(sightings).when(sightingRepository).findAllByBirdIdAndLocationAndTimeFrame(null, "RO", null, null);
        // when
        SightingResponseDto responseDto = sightingService.getSightings(null, "RO", null, null);
        // then
        assertNotNull(responseDto);
        assertEquals(2, responseDto.getSightings().size());
        assertEquals(1L, responseDto.getSightings().get(0).getId());
        assertEquals(2L, responseDto.getSightings().get(1).getId());
    }

    @Test
    public void testCreateSighting_callsRepo() {
        // when
        doReturn(Optional.of(new Bird())).when(birdRepository).findById(1L);
        sightingService.createSighting(1L, "Romania", sampleLocalDatetime);
        // then
        verify(birdRepository, times(1)).findById(any(Long.class));
        verify(sightingRepository, times(1)).save(any(Sighting.class));
    }

    @Test
    public void testCreateSighting_validationFail() {
        assertThrows(SightingValidationException.class, () -> sightingService.createSighting(null, "RO", null));
        assertThrows(SightingValidationException.class, () -> sightingService.createSighting(1L, null, null));
        assertThrows(SightingValidationException.class, () -> sightingService.createSighting(1L, "", null));

        String locationExceedsMaxLen = String.join("", Collections.nCopies(SIGHTING_LOCATION_MAX_LEN + 1, "a"));
        assertThrows(SightingValidationException.class, () -> sightingService.createSighting(1L, locationExceedsMaxLen, null));
    }

    @Test
    public void testCreateSighting_withResults() {
        // given
        Bird bird = new Bird(1L, "bird", "color", 200D, 300D);
        doReturn(Optional.of(bird)).when(birdRepository).findById(any(Long.class));
        Sighting newSighting = new Sighting(1L, bird, "RO", null);
        doReturn(newSighting).when(sightingRepository).save(any(Sighting.class));
        // when
        SightingResponseDto responseDto = sightingService.createSighting(newSighting.getBird().getId(), newSighting.getLocation(), newSighting.getTimestamp());
        // then
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getSightings().size());
        assertNotNull(responseDto.getSightings().get(0));
        assertNotNull(responseDto.getSightings().get(0).getId());
        assertNotNull(responseDto.getSightings().get(0).getBird());
        assertEquals(newSighting.getBird().getId(), responseDto.getSightings().get(0).getBird().getId());
        assertEquals(newSighting.getLocation(), responseDto.getSightings().get(0).getLocation());
        assertEquals(newSighting.getTimestamp(), responseDto.getSightings().get(0).getTimestamp());
    }

    @Test
    public void testUpdateSighting_callsRepo() {
        // when
        Bird bird = new Bird(1L, "bird", "color", null, null);
        Sighting sighting = new Sighting(2L, bird, "Romania", sampleLocalDatetime);
        doReturn(Optional.of(sighting)).when(sightingRepository).findById(2L);
        doReturn(Optional.of(bird)).when(birdRepository).findById(3L);
        sightingService.updateSighting(2L, 3L, "Bucharest", null); // change sighting's birdId
        // then
        verify(sightingRepository, times(1)).findById(any(Long.class));
        verify(birdRepository, times(1)).findById(any(Long.class));
        verify(sightingRepository, times(1)).save(any(Sighting.class));
    }

    @Test
    public void testUpdateSighting_validationFail() {
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(null, 1L, "RO", null));

        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(1L, null, "RO", null));
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(1L, 1L, null, null));
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(1L, 1L, "", null));

        String locationExceedsMaxLen = String.join("", Collections.nCopies(SIGHTING_LOCATION_MAX_LEN + 1, "a"));
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(1L, 1L, locationExceedsMaxLen, null));

        doReturn(Optional.empty()).when(sightingRepository).findById(any(Long.class));
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(2L, 3L , "RO", null)); // sighting id not found

        Bird bird = new Bird(1L, "bird", "color", null, null);
        Sighting sighting = new Sighting(2L, bird, "Romania", sampleLocalDatetime);
        doReturn(Optional.of(sighting)).when(sightingRepository).findById(2L);
        doReturn(Optional.empty()).when(birdRepository).findById(3L);
        assertThrows(SightingValidationException.class, () -> sightingService.updateSighting(2L, 3L, "RO", null)); // bird id not found
    }

    @Test
    public void testUpdateSighting_withResults() {
        // given
        Bird bird = new Bird(1L, "bird", "color", null, null);
        Sighting sighting = new Sighting(2L, bird, "Romania", sampleLocalDatetime);
        doReturn(Optional.of(sighting)).when(sightingRepository).findById(any(Long.class));
        Sighting updatedSighting = new Sighting(1L, bird, "black", null);
        doReturn(updatedSighting).when(sightingRepository).save(sighting);
        // when
        SightingResponseDto responseDto = sightingService.updateSighting(sighting.getId(), sighting.getBird().getId(), sighting.getLocation(), sighting.getTimestamp());
        // then
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getSightings().size());
        assertEquals(updatedSighting.getId(), responseDto.getSightings().get(0).getId());
        assertNotNull(responseDto.getSightings().get(0).getBird());
        assertEquals(updatedSighting.getBird().getId(), responseDto.getSightings().get(0).getBird().getId());
        assertEquals(updatedSighting.getLocation(), responseDto.getSightings().get(0).getLocation());
        assertEquals(updatedSighting.getTimestamp(), responseDto.getSightings().get(0).getTimestamp());
    }

    @Test
    public void testDeleteSighting_callsRepo() {
        // when
        Bird bird = new Bird(1L, "bird", "color", null, null);
        Sighting sighting = new Sighting(2L, bird, "Romania", sampleLocalDatetime);
        Optional<Sighting> optionalSighting = Optional.of(sighting);
        doReturn(optionalSighting).when(sightingRepository).findById(2L);
        sightingService.deleteSighting(sighting.getId());
        // then
        verify(sightingRepository, times(1)).findById(sighting.getId());
        verify(sightingRepository, times(1)).delete(sighting);
    }

    @Test
    public void testDeleteSighting_validationFail() {
        assertThrows(SightingValidationException.class, () -> sightingService.deleteSighting(null));

        // id not found
        doReturn(Optional.empty()).when(sightingRepository).findById(1L);
        assertThrows(SightingValidationException.class, () -> sightingService.deleteSighting(1L));
    }

    @Test
    public void testDeleteSighting_ok() {
        Sighting sighting = new Sighting(2L, new Bird(), "Romania", sampleLocalDatetime);
        doReturn(Optional.of(sighting)).when(sightingRepository).findById(sighting.getId());

        assertDoesNotThrow(() -> sightingService.deleteSighting(sighting.getId()));
    }
}
