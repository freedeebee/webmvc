package de.schad.mi.webmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.schad.mi.webmvc.model.data.Observation;

/**
 * ObservationRepository makes all CRUD Operations for Observation Model available
 */
public interface ObservationRepository extends JpaRepository<Observation, Long> {

}