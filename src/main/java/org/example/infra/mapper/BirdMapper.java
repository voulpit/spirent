package org.example.infra.mapper;

public class BirdMapper {
    /**
     * Maps a Bird model to a Bird entity
     * @param entity Bird entity
     * @return the corresponding Bird model
     */
    public static org.example.domain.model.Bird entityToModel(org.example.infra.entity.Bird entity) {
        if (entity == null) {
            return null;
        }
        return new org.example.domain.model.Bird(
                entity.getId(), entity.getName(), entity.getColor(), entity.getWeight(), entity.getHeight()
        );
    }

    /**
     * Maps a Bird model to a Bird entity
     * @param model Bird model
     * @return the corresponding Bird entity
     */
    public static org.example.infra.entity.Bird modelToEntity(org.example.domain.model.Bird model) {
        if (model == null) {
            return null;
        }
        return new org.example.infra.entity.Bird(
                model.getId(), model.getName(), model.getColor(), model.getWeight(), model.getHeight()
        );
    }
}
