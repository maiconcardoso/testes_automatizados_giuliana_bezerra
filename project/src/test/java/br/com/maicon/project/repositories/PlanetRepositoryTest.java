package br.com.maicon.project.repositories;

import static br.com.maicon.project.common.PlanetConstants.PLANET;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.maicon.project.domain.Planet;

@DataJpaTest
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository repository;

    @Autowired
    private TestEntityManager entityManager;  
    
    @AfterEach
    public void afterEach() {
        PLANET.setId(null);
    }

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet() {
        Planet planet = repository.save(PLANET);

        Planet sut = entityManager.find(Planet.class, planet.getId());

        Assertions.assertThat(sut).isNotNull();
        Assertions.assertThat(sut.getName()).isEqualTo(PLANET.getName());
        Assertions.assertThat(sut.getClimate()).isEqualTo(PLANET.getClimate());
        Assertions.assertThat(sut.getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet("", "", "");

        Assertions.assertThatThrownBy(() -> repository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        Assertions.assertThatThrownBy(() -> repository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void createPlanet_WithExistingName_ThrowsException() {
        Planet planet = entityManager.persistAndFlush(PLANET);
        entityManager.detach(planet);
        planet.setId(null);

        Assertions.assertThatThrownBy(() -> repository.save(planet)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        Planet planet = entityManager.persistFlushFind(PLANET);
        Planet sut = repository.findById(planet.getId()).get();

        Assertions.assertThat(sut).isEqualTo(planet);
        Assertions.assertThat(sut.getId()).isEqualTo(planet.getId());
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnsNotFound() {
        Optional<Planet> planet = repository.findById(1l);

        Assertions.assertThat(planet).isEmpty();
    }

    @Test 
    public void getPlanet_ByExistingName_ReturnsPlanet() {

    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() {

    }

}
