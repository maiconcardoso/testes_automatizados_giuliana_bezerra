package br.com.maicon.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.maicon.project.domain.Planet;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<Planet, Long>{

    public Optional<Planet> findByName(String name);
}
