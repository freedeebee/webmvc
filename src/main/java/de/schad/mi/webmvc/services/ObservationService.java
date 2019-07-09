package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;


/**
 *  ObservationService Interface show all Methods regarding to handle with Observations
 */
public interface ObservationService {

    /**
     * Will find all Observation in Database
     * 
     * @return a List of all Observations
     */
    List<Observation> findAll();

    /**
     * Find a special Observation with their ID
     * 
     * @param id of a observation
     * @return a Observation
     */
    Optional<Observation> findById(long id);

    /**
     * Saves a Observation in the Database
     * 
     * @param observation a Entity of an observation
     */
    void save(Observation observation);

    /**
     * Deleting a Observation in the Database
     * 
     * @param observation
     */
    void delete(Observation observation);

    /**
     *  convert method will convert a Form Observation to an Entity Observation
     *
     * @param username who created the Observation
     * @param observation Observation from Form
     * @param filename filename of an optional Picture
     * @return a Observation Entity
     */
    Observation convert(String username, ObservationCreationForm observation, String filename);
    /**
     * convertBack method will convert a Entity Observation to an Observation Form Model 
     * to be able to edit a Observation
     * 
     * @param observation a Observation
     * @return the converted Form Observation
     */
    ObservationCreationForm convertBack(Observation observation);

    /**
     * override method will Update a Observation
     * 
     * @param id the Id of a observation
     * @param observation the new Observation
     */
	void override(long id, Observation observation);
}