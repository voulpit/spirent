package org.example.service;

import org.example.domain.dto.SightingResponseDto;

import java.time.LocalDateTime;

public interface SightingService {
    /**
     * Gets all the sightings from the database.
     * @return a dto that contains an array of Sighting
     */
    SightingResponseDto getSightings();

    /**
     * Gets sightings according to criteria. If none of the criteria is provided, it retrieves all birds.
     * If one of the criteria is provided, it uses only that criterion.
     * @param birdId id of the sighted bird
     * @param location location of the sighting
     * @param from start of the time interval of the sighting
     * @param until end of the time interval of the sighting
     * @return a dto that contains an array of Sighting
     */
    SightingResponseDto getSightings(Long birdId, String location, LocalDateTime from, LocalDateTime until);

    /**
     * Creates a new sighting according to given attributes.
     * @param birdId id of the sighted bird, mandatory
     * @param location location of the sighting, mandatory
     * @param timestamp date-time of the sighting, optional (nullable)
     * @return a dto that contains an array of size one which has the newly created Sighting.
     */
    SightingResponseDto createSighting(Long birdId, String location, LocalDateTime timestamp);

    /**
     * Updates a Sighting by setting all the given attributes in the Sighting object.
     * @param id id of the existing sighting, mandatory
     * @param birdId new birdId for the sighting, mandatory
     * @param location new location for the sighting, mandatory
     * @param timestamp new timestamp for the sighting, optional (nullable)
     * @return a dto that contains an array of size one which has the updated Sighting.
     */
    SightingResponseDto updateSighting(Long id, Long birdId, String location, LocalDateTime timestamp);

    /**
     * Deletes a Sighting by its id
     * @param id id of the Sighting in the database
     */
    void deleteSighting(Long id);
}
