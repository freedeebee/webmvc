package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;

public interface ObservationService {

    List<Observation> findAll();
    Optional<Observation> findById(long id);
    void save(Observation observation);
    void delete(Observation observation);
    Observation convert(ObservationCreationForm observation, String filename);
    ObservationCreationForm convertBack(Observation observation);
}