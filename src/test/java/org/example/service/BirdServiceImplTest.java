package org.example.service;

import org.example.domain.dto.BirdResponseDto;
import org.example.domain.exception.BirdValidationException;
import org.example.infra.entity.Bird;
import org.example.infra.repo.BirdRepository;
import org.example.service.impl.BirdServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.example.util.Constants.BIRD_COLOR_MAX_LEN;
import static org.example.util.Constants.BIRD_NAME_MAX_LEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BirdServiceImplTest {
    @Mock
    private BirdRepository birdRepository;
    @InjectMocks
    private BirdServiceImpl birdService;

    @Test
    public void testGetBirds_callsRepo() {
        // when
        birdService.getBirds();
        // then
        verify(birdRepository, times(1)).findAll();
    }

    @Test
    public void testGetBirds_withResults() {
        // given
        List<Bird> birds = new ArrayList<>();
        birds.add(new Bird());
        birds.add(new Bird(1L, "bird", "color", 5D, 5D));
        doReturn(birds).when(birdRepository).findAll();
        // when
        BirdResponseDto responseDto = birdService.getBirds();
        // then
        assertNotNull(responseDto);
        assertEquals(2, responseDto.getBirds().size());
        assertEquals("bird", responseDto.getBirds().get(1).getName());
    }

    @Test
    public void testGetBirds_filtered_callsRepo() {
        // when
        birdService.getBirds(null, null);
        // then
        verify(birdRepository, times(1)).findAll();

        // when
        birdService.getBirds("bird", null);
        // then
        verify(birdRepository, times(1)).findAllByName("bird");

        // when
        birdService.getBirds(null, "color");
        // then
        verify(birdRepository, times(1)).findAllByColor("color");

        // when
        birdService.getBirds("bird", "color");
        // then
        verify(birdRepository, times(1)).findAllByNameAndColor("bird", "color");
    }

    @Test
    public void testGetBirds_filtered_withResults() {
        // given
        List<Bird> birds = new ArrayList<>();
        birds.add(new Bird(2L, "bird", "color", 10D, 15D));
        birds.add(new Bird(1L, "bird", "color", 5D, 5D));
        doReturn(birds).when(birdRepository).findAllByNameAndColor("bird", "color");
        // when
        BirdResponseDto responseDto = birdService.getBirds("bird", "color");
        // then
        assertNotNull(responseDto);
        assertEquals(2, responseDto.getBirds().size());
        assertEquals("bird", responseDto.getBirds().get(0).getName());
        assertEquals("color", responseDto.getBirds().get(0).getColor());
        assertEquals(10D, responseDto.getBirds().get(0).getWeight());
        assertEquals("bird", responseDto.getBirds().get(1).getName());
        assertEquals("color", responseDto.getBirds().get(1).getColor());
        assertEquals(5D, responseDto.getBirds().get(1).getWeight());
    }

    @Test
    public void testCreateBird_callsRepo() {
        // when
        birdService.createBird("bird", "color", null, null);
        // then
        verify(birdRepository, times(1)).save(any(Bird.class));
    }

    @Test
    public void testCreateBird_validationFail() {
        assertThrows(BirdValidationException.class, () -> birdService.createBird(null, "color", null, null));
        assertThrows(BirdValidationException.class, () -> birdService.createBird("bird", null, null, null));
        assertThrows(BirdValidationException.class, () -> birdService.createBird(null, "", null, null));
        assertThrows(BirdValidationException.class, () -> birdService.createBird("", "color", null, null));

        String nameExceedsMaxLen = String.join("", Collections.nCopies(BIRD_NAME_MAX_LEN + 1, "a"));
        assertThrows(BirdValidationException.class, () -> birdService.createBird(nameExceedsMaxLen, "color", 1000D, 10D));

        String colorExceedsMaxLen = String.join("", Collections.nCopies(BIRD_COLOR_MAX_LEN + 1, "b"));
        assertThrows(BirdValidationException.class, () -> birdService.createBird("bird", colorExceedsMaxLen, 1000D, 10D));

        assertThrows(BirdValidationException.class, () -> birdService.createBird("bird", "color", 0D, 10D));
        assertThrows(BirdValidationException.class, () -> birdService.createBird("bird", "color", 1000D, -10D));
    }

    @Test
    public void testCreateBird_withResults() {
        // given
        Bird newBird = new Bird(1L, "bird", "color", 5D, 5D);
        doReturn(newBird).when(birdRepository).save(any(Bird.class));
        // when
        BirdResponseDto responseDto = birdService.createBird(newBird.getName(), newBird.getColor(), newBird.getWeight(), newBird.getHeight());
        // then
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getBirds().size());
        assertNotNull(responseDto.getBirds().get(0));
        assertNotNull(responseDto.getBirds().get(0).getId());
        assertEquals(newBird.getName(), responseDto.getBirds().get(0).getName());
        assertEquals(newBird.getColor(), responseDto.getBirds().get(0).getColor());
        assertEquals(newBird.getWeight(), responseDto.getBirds().get(0).getWeight());
        assertEquals(newBird.getHeight(), responseDto.getBirds().get(0).getHeight());
    }

    @Test
    public void testUpdateBird_callsRepo() {
        // when
        org.example.infra.entity.Bird bird = new org.example.infra.entity.Bird(1L, "bird", "color", null, null);
        Optional<Bird> optionalBird = Optional.of(bird);
        doReturn(optionalBird).when(birdRepository).findById(1L);
        birdService.updateBird(bird.getId(), bird.getName(), bird.getColor(), bird.getWeight(), bird.getHeight());
        // then
        verify(birdRepository, times(1)).findById(bird.getId());
        verify(birdRepository, times(1)).save(bird);
    }

    @Test
    public void testUpdateBird_validationFail() {
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(null, "name", "color", null, null));

        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, null, "color", null, null));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, "bird", null, null, null));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, null, "", null, null));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, "", "color", null, null));

        String nameExceedsMaxLen = String.join("", Collections.nCopies(BIRD_NAME_MAX_LEN + 1, "a"));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, nameExceedsMaxLen, "color", 1000D, 10D));

        String colorExceedsMaxLen = String.join("", Collections.nCopies(BIRD_COLOR_MAX_LEN + 1, "b"));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, "bird", colorExceedsMaxLen, 1000D, 10D));

        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, "bird", "color", 0D, 10D));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(1L, "bird", "color", 1000D, -10D));

        doReturn(Optional.empty()).when(birdRepository).findById(any(Long.class));
        assertThrows(BirdValidationException.class, () -> birdService.updateBird(2L, "bird", "color", 1000D, 40D)); // bird id not found
    }

    @Test
    public void testUpdateBird_withResults() {
        // given
        Bird bird = new Bird(1L, "bird", "color", 5D, 5D);
        doReturn(Optional.of(bird)).when(birdRepository).findById(any(Long.class));
        Bird updatedBird = new Bird(1L, "flying bird", "color2", 50D, 50D);
        doReturn(updatedBird).when(birdRepository).save(bird);
        // when
        BirdResponseDto responseDto = birdService.updateBird(bird.getId(), bird.getName(), bird.getColor(), bird.getWeight(), bird.getHeight());
        // then
        assertNotNull(responseDto);
        assertEquals(1, responseDto.getBirds().size());
        assertEquals(updatedBird.getId(), responseDto.getBirds().get(0).getId());
        assertEquals(updatedBird.getName(), responseDto.getBirds().get(0).getName());
        assertEquals(updatedBird.getColor(), responseDto.getBirds().get(0).getColor());
        assertEquals(updatedBird.getWeight(), responseDto.getBirds().get(0).getWeight());
        assertEquals(updatedBird.getHeight(), responseDto.getBirds().get(0).getHeight());
    }

    @Test
    public void testDeleteBird_callsRepo() {
        // when
        org.example.infra.entity.Bird bird = new org.example.infra.entity.Bird(1L, null, null, null, null);
        Optional<Bird> optionalBird = Optional.of(bird);
        doReturn(optionalBird).when(birdRepository).findById(1L);
        birdService.deleteBird(bird.getId());
        // then
        verify(birdRepository, times(1)).findById(bird.getId());
        verify(birdRepository, times(1)).delete(bird);
    }

    @Test
    public void testDeleteBird_validationFail() {
        assertThrows(BirdValidationException.class, () -> birdService.deleteBird(null));

        // id not found
        doReturn(Optional.empty()).when(birdRepository).findById(1L);
        assertThrows(BirdValidationException.class, () -> birdService.deleteBird(1L));
    }

    @Test
    public void testDeleteBird_ok() {
        Bird bird = new Bird(1L, "bird", "color", 5D, 5D);
        doReturn(Optional.of(bird)).when(birdRepository).findById(bird.getId());

        assertDoesNotThrow(() -> birdService.deleteBird(bird.getId()));
    }
}
