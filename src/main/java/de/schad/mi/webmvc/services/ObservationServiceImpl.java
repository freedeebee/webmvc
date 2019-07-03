package de.schad.mi.webmvc.services;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.schad.mi.webmvc.exceptions.SichtungNotFoundException;
import de.schad.mi.webmvc.model.ObservationCreationForm;
import de.schad.mi.webmvc.model.data.ImageMeta;
import de.schad.mi.webmvc.model.data.Observation;
import de.schad.mi.webmvc.repository.ObservationRepository;

@Service
public class ObservationServiceImpl implements ObservationService {

    private final Logger logger = LoggerFactory.getLogger(ObservationServiceImpl.class);

    private final ObservationRepository repository;
    private final ImageService imageService;

    @Autowired
    public ObservationServiceImpl(ObservationRepository repository, ImageService imageService) {
        this.repository = repository;
        this.imageService = imageService;
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
    public Observation convert(String username, ObservationCreationForm observation, String filename) {
        Observation cObservation = new Observation();
        cObservation.setFinder(username);
        cObservation.setLocation(observation.getLocation());
        cObservation.setDate(observation.getDate());
        cObservation.setDaytime(observation.getDaytime());
        cObservation.setDescription(observation.getDescription());
        cObservation.setRating(observation.getRating());
        cObservation.setImage(filename);

        try {
            ImageMeta meta = imageService.getMetaData(new FileInputStream(filename));
            cObservation.setLongitude(meta.getLongitude());
            cObservation.setLatitude(meta.getLatitude());
        } catch (Exception e) {
            logger.warn("Something went wrong");
            cObservation.setLongitude(0);
            cObservation.setLatitude(0);
        }

        return cObservation;
    }

    @Override
    public ObservationCreationForm convertBack(Observation observation) {
        ObservationCreationForm formObservation = new ObservationCreationForm();
        formObservation.setDescription(observation.getDescription());
        formObservation.setRating(observation.getRating());
        formObservation.setLocation(observation.getLocation());
        formObservation.setDate(observation.getDate());
        formObservation.setDaytime(observation.getDaytime());
        return formObservation;
    }

    @Override
    public void override(long id, Observation observation) {
        Observation tempObservation = repository.findById(id)
            .orElseThrow(() -> new SichtungNotFoundException("Observation not found"));
        tempObservation.setComments(observation.getComments());
        tempObservation.setDate(observation.getDate());
        tempObservation.setDaytime(observation.getDaytime());
        tempObservation.setDescription(observation.getDescription());
        tempObservation.setFinder(observation.getFinder());
        tempObservation.setImage(observation.getImage());
        tempObservation.setLocation(observation.getLocation());
        tempObservation.setRating(observation.getRating());
        repository.flush();
    }


}