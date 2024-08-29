package org.example.controller;

import org.example.domain.dto.BirdResponseDto;
import org.example.domain.dto.CreateBirdRequestDto;
import org.example.domain.dto.UpdateBirdRequestDto;
import org.example.domain.model.Bird;
import org.example.service.BirdService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BirdControllerTest {
    @Mock
    private BirdService birdService;
    @InjectMocks
    private BirdController birdController;

    @Test
    public void testGetAllBirds_callsService() {
        // when
        birdController.getAllBirds();
        // then
        verify(birdService, times(1)).getBirds();
    }

    @Test
    public void testGetAllBirds_emptyResults() {
        // given
        BirdResponseDto emptyRes = new BirdResponseDto();
        doReturn(emptyRes).when(birdService).getBirds();
        // when
        ResponseEntity<BirdResponseDto> responseEntity = birdController.getAllBirds();
        // then
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody().getBirds().isEmpty());
    }

    @Test
    public void testGetAllBirds_withResults() {
        // given
        BirdResponseDto result = new BirdResponseDto();
        result.getBirds().add(new Bird());
        result.getBirds().add(new Bird());
        doReturn(result).when(birdService).getBirds();
        // when
        ResponseEntity<BirdResponseDto> responseEntity = birdController.getAllBirds();
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().getBirds().size());
    }

    @Test
    public void testGetBirds_callsService() {
        // when
        birdController.getBirds("", "");
        // then
        verify(birdService, times(1)).getBirds("", "");
    }

    @Test
    public void testGetBirds_withResults() {
        // given
        BirdResponseDto result = new BirdResponseDto();
        result.getBirds().add(new Bird());
        when(birdService.getBirds(any(String.class), any(String.class))).thenReturn(result);
        // when
        ResponseEntity<BirdResponseDto> responseEntity = birdController.getBirds("crow", "black");
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getBirds().size());
    }

    @Test
    public void testGetBird_callsService() {
        // when
        birdController.getBird(1L);
        // then
        verify(birdService, times(1)).getBird(1L);
    }

    @Test
    public void testCreateBird_callsService() {
        // when
        birdController.createBird(new CreateBirdRequestDto());
        // then
        verify(birdService, times(1)).createBird(null, null, null, null);
    }

    @Test
    public void testCreateBird_withResults() {
        // given
        CreateBirdRequestDto requestDto = new CreateBirdRequestDto("crow", "black", 500D, 50D);
        BirdResponseDto responseDto = new BirdResponseDto();
        responseDto.getBirds().add(new Bird(1L, "crow", "black", 500D, 50D));
        when(birdService.createBird(any(String.class), any(String.class), any(Double.class), any(Double.class))).thenReturn(responseDto);
        // when
        ResponseEntity<BirdResponseDto> responseEntity = birdController.createBird(requestDto);
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getBirds().size());
        assertEquals(responseDto.getBirds().get(0), responseEntity.getBody().getBirds().get(0));
    }

    @Test
    public void testUpdateBird_callsService() {
        // when
        birdController.updateBird(new UpdateBirdRequestDto(2L, "dove", "gray", 300D, 30D));
        // then
        verify(birdService, times(1)).updateBird(2L, "dove", "gray", 300D, 30D);
    }

    @Test
    public void testUpdateBird_withResults() {
        // given
        UpdateBirdRequestDto requestDto = new UpdateBirdRequestDto(2L, "dove", "gray", 300D, 30D);
        BirdResponseDto responseDto = new BirdResponseDto();
        responseDto.getBirds().add(new Bird(2L, "dove", "gray", 300D, 30D));
        when(birdService.updateBird(any(Long.class), any(String.class), any(String.class), any(Double.class), any(Double.class))).thenReturn(responseDto);
        // when
        ResponseEntity<BirdResponseDto> responseEntity = birdController.updateBird(requestDto);
        // then
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().getBirds().size());
        assertEquals(requestDto.getId(), responseEntity.getBody().getBirds().get(0).getId());
        assertEquals(requestDto.getName(), responseEntity.getBody().getBirds().get(0).getName());
        assertEquals(requestDto.getColor(), responseEntity.getBody().getBirds().get(0).getColor());
        assertEquals(requestDto.getWeight(), responseEntity.getBody().getBirds().get(0).getWeight());
        assertEquals(requestDto.getHeight(), responseEntity.getBody().getBirds().get(0).getHeight());
    }

    @Test
    public void testDeleteBird_callsService() {
        // when
        birdController.deleteBird(1L);
        // then
        verify(birdService, times(1)).deleteBird(1L);
    }

    @Test
    public void testDeleteBird_withResults() {
        // when
        ResponseEntity<Boolean> responseEntity = birdController.deleteBird(1L);
        // then
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody());
    }
}
