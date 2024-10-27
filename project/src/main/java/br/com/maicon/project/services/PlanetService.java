package br.com.maicon.project.services;

import org.springframework.stereotype.Service;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.repositories.PlanetRepository;

@Service
public class PlanetService {

    private PlanetRepository repository;

    public PlanetService(PlanetRepository planetRepository) {
        this.repository = planetRepository;
    }

    public Planet create(Planet planet) {
        return repository.save(planet);
    }

}
