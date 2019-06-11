package de.schad.mi.webmvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.repository.ObservationRepository;

@Service
public class ObservationServiceImpl implements ObservationService {

    private final ObservationRepository repository;

    @Autowired
    public ObservationServiceImpl(ObservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Observation> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Observation> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public void save(Observation observation) {
        repository.save(observation);
    }

    @Override
    public void delete(Observation observation) {
        repository.delete(observation);
    }

    @Override
    public Observation convert(ObservationCreationForm observation) {
        Observation cObservation = new Observation();
        cObservation.setFinder(observation.getFinder());
        cObservation.setLocation(observation.getLocation());
        cObservation.setDate(observation.getDate());
        cObservation.setDaytime(observation.getDaytime());
        cObservation.setDescription(observation.getDescription());
        cObservation.setRating(observation.getRating());
        cObservation.setImage(observation.getImage().getOriginalFilename());
        
        return cObservation;
    }
    
}