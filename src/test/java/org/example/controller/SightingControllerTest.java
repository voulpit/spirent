package org.example.controller;

import org.example.domain.dto.CreateSightingRequestDto;
import org.example.domain.dto.SightingResponseDto;
import org.example.domain.dto.UpdateSightingRequestDto;
import org.example.domain.model.Bird;
import org.example.domain.model.Sighting;
import org.example.service.SightingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SightingControllerTest {
    @Mock
    SightingService sightingService;
    @InjectMocks
    SightingController sightingController;

    @Test
    public void testGetAllSightings_callsService() {
        // when
        sightingController.getAllSightings();
        // then
        verify(sightingService, times(1)).getSightings();
    }

    @Test
    public void testGetAllSightings_withResults() {
        // given
        SightingResponseDto result = new SightingResponseDto();
        result.getSightings().add(new Sighting());
        result.getSightings().add(new Sighting());
        result.getSightings().add(new Sighting());
        doReturn(result).when(sightingService).getSightings();
        // when
        ResponseEntity<SightingResponseDto> responseEntity = sightingController.getAllSightings();
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(3, responseEntity.getBody().getSightings().size());
    }

    @Test
    public void testGetSightings_callsService() {
        // when
        sightingController.getSightings(1L, "Bucharest", "2024-08-24T08:00:01.000000", "2024-08-24T08:00:01.000000");
        // then
        verify(sightingService, times(1)).getSightings(1L, "Bucharest", LocalDateTime.parse("2024-08-24T08:00:01.000000"), LocalDateTime.parse("2024-08-24T08:00:01.000000"));
    }

    @Test
    public void testGetSightings_withResults() {
        // given
        SightingResponseDto result = new SightingResponseDto();
        result.getSightings().add(new Sighting());
        when(sightingService.getSightings(any(Long.class), any(String.class), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(result);
        // when
        ResponseEntity<SightingResponseDto> responseEntity = sightingController.getSightings(1L, "Bucharest", "2024-08-24T08:00:01.000000", "2024-08-24T08:00:01.000000");
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getSightings().size());
    }

    @Test
    public void testCreateSighting_callsService() {
        // when
        sightingController.createSighting(new CreateSightingRequestDto());
        // then
        verify(sightingService, times(1)).createSighting(null, null, null);
    }

    @Test
    public void testCreateSighting_withResults() {
        // given
        CreateSightingRequestDto requestDto = new CreateSightingRequestDto(1L, "Ploiesti", LocalDateTime.parse("2024-08-24T08:00:01.000000"));
        SightingResponseDto responseDto = new SightingResponseDto();
        responseDto.getSightings().add(new Sighting(new Bird(1L, null, null, null, null), "Ploiesti", LocalDateTime.parse("2024-08-24T08:00:01.000000")));
        when(sightingService.createSighting(requestDto.getBirdId(), requestDto.getLocation(), requestDto.getTimestamp())).thenReturn(responseDto);
        // when
        ResponseEntity<SightingResponseDto> responseEntity = sightingController.createSighting(requestDto);
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getSightings().size());
        assertEquals(responseDto.getSightings().get(0), responseEntity.getBody().getSightings().get(0));
    }

    @Test
    public void testUpdateSighting_callsService() {
        // when
        sightingController.updateSighting(new UpdateSightingRequestDto(1L, 1L, "Pitesti", LocalDateTime.parse("2024-08-24T08:00:01.000000")));
        // then
        verify(sightingService, times(1)).updateSighting(1L, 1L, "Pitesti", LocalDateTime.parse("2024-08-24T08:00:01.000000"));
    }

    @Test
    public void testUpdateSighting_withResults() {
        // given
        UpdateSightingRequestDto requestDto = new UpdateSightingRequestDto(1L, 1L, "Pitesti", LocalDateTime.parse("2024-08-24T08:00:01.000000"));
        SightingResponseDto responseDto = new SightingResponseDto();
        responseDto.getSightings().add(new Sighting(1L, new Bird(1L, null, null, null, null), "Pitesti", LocalDateTime.parse("2024-08-24T08:00:01.000000")));
        when(sightingService.updateSighting(any(Long.class), any(Long.class), any(String.class), any(LocalDateTime.class))).thenReturn(responseDto);
        // when
        ResponseEntity<SightingResponseDto> responseEntity = sightingController.updateSighting(requestDto);
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getSightings().size());
        assertEquals(requestDto.getId(), responseEntity.getBody().getSightings().get(0).getId());
        assertEquals(requestDto.getBirdId(), responseEntity.getBody().getSightings().get(0).getBird().getId());
        assertEquals(requestDto.getLocation(), responseEntity.getBody().getSightings().get(0).getLocation());
        assertEquals(requestDto.getTimestamp(), responseEntity.getBody().getSightings().get(0).getTimestamp());
    }

    @Test
    public void testDeleteSighting_callsService() {
        // when
        sightingController.deleteSighting(1L);
        // then
        verify(sightingService, times(1)).deleteSighting(1L);
    }

    @Test
    public void testDeleteSighting_withResults() {
        // when
        ResponseEntity<Boolean> responseEntity = sightingController.deleteSighting(1L);
        // then
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody());
    }
}
