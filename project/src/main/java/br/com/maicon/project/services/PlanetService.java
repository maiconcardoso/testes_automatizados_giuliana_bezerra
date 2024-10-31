package br.com.maicon.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.repositories.PlanetRepository;
import br.com.maicon.project.until.QueryBuilder;

@Service
public class PlanetService {

    private PlanetRepository repository;

    public PlanetService(PlanetRepository planetRepository) {
        this.repository = planetRepository;
    }

    public Planet create(Planet planet) {
        return repository.save(planet);
    }

    public Optional<Planet> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Planet> findByName(String name) {
        return repository.findByName(name);
    }

    public List<Planet> findAll(String climate, String terrain) {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));
        return repository.findAll(query);
    }

}
