package org.example.service;

import org.example.domain.dto.BirdResponseDto;

public interface BirdService {
    /**
     * Gets all the birds from the database.
     * @return a dto that contains an array of Bird
     */
    BirdResponseDto getBirds();

    /**
     * Gets birds according to criteria. If none of the criteria is provided, it retrieves all birds.
     * If one of the criteria is provided, it uses only that criterion.
     * @param name name of the bird, optional (nullable)
     * @param color color of the bird, optional (nullable)
     * @return a dto that contains an array of Bird
     */
    BirdResponseDto getBirds(String name, String color);

    /**
     * Retrieves one Bird by id.
     * @return a dto that contains an array of size one which has the retrieved Bird.
     */
    BirdResponseDto getBird(Long id);

    /**
     * Creates a new bird according to given characteristics.
     * @param name name of the bird, mandatory
     * @param color color of the bird, mandatory
     * @param weight weight of the bird, optional (nullable)
     * @param height height of the bird, optional (nullable)
     * @return a dto that contains an array of size one which has the newly created Bird.
     */
    BirdResponseDto createBird(String name, String color, Double weight, Double height);

    /**
     * Updates a bird by setting all the given attributes in the Bird object.
     * @param id id of the existing bird, mandatory
     * @param name new name for the bird, mandatory
     * @param color new color for the bird, mandatory
     * @param weight new weight for the bird, optional (nullable)
     * @param height new height for the bird, optional (nullable)
     * @return a dto that contains an array of size one which has the updated Bird.
     */
    BirdResponseDto updateBird(Long id, String name, String color, Double weight, Double height);

    /**
     * Deletes a Bird by its id
     * @param id id of the Bird in the database
     */
    void deleteBird(Long id);
}
