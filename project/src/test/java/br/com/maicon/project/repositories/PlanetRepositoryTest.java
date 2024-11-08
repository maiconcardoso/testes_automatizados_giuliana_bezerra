package br.com.maicon.project.repositories;

import static br.com.maicon.project.common.PlanetConstants.PLANET;
import static br.com.maicon.project.common.PlanetConstants.TATOOINE;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.until.QueryBuilder;

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
        Planet planet = entityManager.persistFlushFind(PLANET);
        Planet sut = repository.findByName(planet.getName()).get();

        Assertions.assertThat(sut).isEqualTo(planet);
        Assertions.assertThat(sut.getName()).isEqualTo(planet.getName());
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnsNotFound() {
        Optional<Planet> sut = repository.findByName("name");

        Assertions.assertThat(sut).isEmpty();
    }

    /* @Sql(scripts = "/import.sql")
    @Test  */
    public void listPlanets_ReturnsFilteredPlanets() throws Exception {
        Example<Planet> queryWithoutFilters = QueryBuilder.makeQuery(new Planet());
        Example<Planet> queryWithFilters = QueryBuilder.makeQuery(new Planet(TATOOINE.getClimate(), TATOOINE.getTerrain()));

        List<Planet> responseWithoutFilters = repository.findAll(queryWithoutFilters);
        List<Planet> responseWithFilters = repository.findAll(queryWithFilters);
        
        Assertions.assertThat(responseWithoutFilters).isEmpty();
        Assertions.assertThat(responseWithFilters).hasSize(3);
        Assertions.assertThat(responseWithFilters).isNotEmpty();
        Assertions.assertThat(responseWithFilters).hasSize(1);
        Assertions.assertThat(responseWithFilters.get(0)).isEqualTo(TATOOINE);
    }

    @Test
    public void listPlanets_ReturnsNoPlanets() throws Exception {
        Example<Planet> query = QueryBuilder.makeQuery(new Planet());

        List<Planet> response = repository.findAll(query);

        Assertions.assertThat(response).isEmpty();
    }

}
