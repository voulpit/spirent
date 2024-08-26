package org.example.infra.mapper;

import org.example.infra.entity.Bird;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SightingMapperTest {
    @Test
    public void testEntityToModel_null() {
        assertNull(SightingMapper.entityToModel(null));
    }

    @Test
    public void testEntityToModel() {
        org.example.infra.entity.Sighting entity = new org.example.infra.entity.Sighting(1L,
                new Bird(1L, "sparrow", null, null, null), "Bucharest", LocalDateTime.parse("2024-08-24T08:00:01.000000"));
        org.example.domain.model.Sighting result = SightingMapper.entityToModel(entity);
        assertEquals(1L, result.getId());
        assertEquals("sparrow", result.getBird().getName());
        assertEquals("Bucharest", result.getLocation());
        assertEquals(LocalDateTime.parse("2024-08-24T08:00:01.000000"), result.getTimestamp());
    }

    @Test
    public void testModelToEntity_null() {
        assertNull(SightingMapper.modelToEntity(null));
    }

    @Test
    public void testModelToEntity() {
        org.example.domain.model.Sighting model = new org.example.domain.model.Sighting(7L,
                new org.example.domain.model.Bird(1L, "swallow", null, null, null),
                "Bucharest", LocalDateTime.parse("2024-08-24T08:00:01.000000"));
        org.example.infra.entity.Sighting result = SightingMapper.modelToEntity(model);
        assertEquals(7L, result.getId());
        assertEquals("swallow", result.getBird().getName());
        assertEquals("Bucharest", result.getLocation());
        assertEquals(LocalDateTime.parse("2024-08-24T08:00:01.000000"), result.getTimestamp());
    }
}
