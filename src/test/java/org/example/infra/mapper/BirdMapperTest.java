package org.example.infra.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BirdMapperTest {

    @Test
    public void testEntityToModel_null() {
        assertNull(BirdMapper.entityToModel(null));
    }

    @Test
    public void testEntityToModel() {
        org.example.infra.entity.Bird entity = new org.example.infra.entity.Bird(1L, "sparrow", "blue", 200D, 10D);
        org.example.domain.model.Bird result = BirdMapper.entityToModel(entity);
        assertEquals(1L, result.getId());
        assertEquals("sparrow", result.getName());
        assertEquals("blue", result.getColor());
        assertEquals(200D, result.getWeight());
        assertEquals(10D, result.getHeight());
    }

    @Test
    public void testModelToEntity_null() {
        assertNull(BirdMapper.modelToEntity(null));
    }

    @Test
    public void testModelToEntity() {
        org.example.domain.model.Bird model = new org.example.domain.model.Bird(5L, "swallow", "black", 200D, 10D);
        org.example.infra.entity.Bird result = BirdMapper.modelToEntity(model);
        assertEquals(5L, result.getId());
        assertEquals("swallow", result.getName());
        assertEquals("black", result.getColor());
        assertEquals(200D, result.getWeight());
        assertEquals(10D, result.getHeight());
    }
}
