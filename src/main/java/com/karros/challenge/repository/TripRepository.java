package com.karros.challenge.repository;


import com.karros.challenge.domain.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the {@link Trip} entity.
 */
public interface TripRepository extends JpaRepository<Trip, Long> {
    Page<Trip> findByCreatedById(long createdById, Pageable pageable);

    Trip findOneById(long id);
}
