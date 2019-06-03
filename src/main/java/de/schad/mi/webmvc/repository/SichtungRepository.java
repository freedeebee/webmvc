package de.schad.mi.webmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.schad.mi.webmvc.model.data.Sichtung;


public interface SichtungRepository extends JpaRepository<Sichtung, Long> {

}