package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;

public interface ObservationService {

    public List<Observation> findAll();
    public Optional<Observation> findById(long id);
    public void save(Observation observation);
    public void delete(Observation observation);
    public Observation convert(ObservationCreationForm observation);
}