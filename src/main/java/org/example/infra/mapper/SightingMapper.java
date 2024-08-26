package org.example.infra.mapper;

public class SightingMapper {
    /**
     * Maps a Sighting entity to a Sighting model
     * @param entity Sighting entity
     * @return the corresponding Sighting model
     */
    public static org.example.domain.model.Sighting entityToModel(org.example.infra.entity.Sighting entity) {
        if (entity == null) {
            return null;
        }
        return new org.example.domain.model.Sighting(
                entity.getId(), BirdMapper.entityToModel(entity.getBird()), entity.getLocation(), entity.getTimestamp()
        );
    }

    /**
     * Maps a Sighting model to a Sighting entity
     * @param model Sighting model
     * @return the corresponding Sighting entity
     */
    public static org.example.infra.entity.Sighting modelToEntity(org.example.domain.model.Sighting model) {
        if (model == null) {
            return null;
        }
        return new org.example.infra.entity.Sighting(
                model.getId(), BirdMapper.modelToEntity(model.getBird()), model.getLocation(), model.getTimestamp()
        );
    }
}
