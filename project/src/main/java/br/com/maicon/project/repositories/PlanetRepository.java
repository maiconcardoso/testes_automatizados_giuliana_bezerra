package br.com.maicon.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.maicon.project.domain.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long>{

}
