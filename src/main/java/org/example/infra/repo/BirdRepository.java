package org.example.infra.repo;

import org.example.infra.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {
    /**
     * Finds all Bird entities by name
     * @param name attribute name
     * @return list of matching Birds
     */
    List<Bird> findAllByName(String name);

    /**
     * Finds all Bird entities by color
     * @param color attribute color
     * @return list of matching Birds
     */
    List<Bird> findAllByColor(String color);

    /**
     * Finds all Bird entities by name and color
     * @param name attribute name
     * @param color attribute color
     * @return list of matching Birds
     */
    List<Bird> findAllByNameAndColor(String name, String color);
}
