package no.josefushighscore.register;

import no.josefushighscore.model.TrackUserSignins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository interface for accessing and managing user sign-in tracking data.
 * Extends JpaRepository to inherit standard CRUD operations.
 */
@Repository
public interface UserSignInRegister extends JpaRepository<TrackUserSignins, Long> {
    
    /**
     * Find user sign-in tracking record by user ID
     * @param userId the ID of the user
     * @return an Optional containing the tracking record if found
     */
    Optional<TrackUserSignins> findByUserId(Long userId);

    /**
     * Find the most recent sign-in record for a specific user
     * @param userId the ID of the user
     * @return an Optional containing the most recent tracking record if found
     */
    Optional<TrackUserSignins> findTopByUserIdOrderByLastSignInDesc(Long userId);
    
    /**
     * Delete tracking records for a specific user
     * @param userId the ID of the user
     */
    void deleteByUserId(Long userId);

    Optional<TrackUserSignins> findTopByUserIdAndLastSignInBeforeOrderByLastSignInDesc(Long userId, LocalDateTime now);
}