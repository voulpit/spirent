package org.example.infra.repo;

import org.example.infra.entity.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SightingRepository extends JpaRepository<Sighting, Long>  {
    /**
     * Finds all Sighting entities by given attributes. Some of the attributes can be missing.
     * @param birdId attribute birdId, optional (nullable)
     * @param location attribute location, optional (nullable)
     * @param timeFrom start of the time interval, optional (nullable)
     * @param timeUntil end of the time interval, optional (nullable)
     * @return list of matching Sightings
     */
    @Query("select s from Sighting s where (?1 is null or s.bird.id=?1) and (?2 is null or s.location=?2) and " +
            "(?3 is null or ?4 is null or (DATE(s.timestamp)>=DATE(?3) and DATE(s.timestamp)<=DATE(?4)))")
    List<Sighting> findAllByBirdIdAndLocationAndTimeFrame(Long birdId, String location, LocalDateTime timeFrom, LocalDateTime timeUntil);
}
