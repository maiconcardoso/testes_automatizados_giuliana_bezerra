package br.com.maicon.project.PlanetIT;

import static br.com.maicon.project.common.PlanetConstants.PLANET;
import static br.com.maicon.project.common.PlanetConstants.TATOOINE;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.maicon.project.domain.Planet;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PlanetIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createPlanet_ReturnsCreated() {
        ResponseEntity<Planet> sut = restTemplate.postForEntity("/planets", PLANET, Planet.class);

        Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(sut.getBody().getId()).isNotNull();
        Assertions.assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
        Assertions.assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
        Assertions.assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());
    }

    @Test
    public void getPlanet_ReturnsPlanet() {
        ResponseEntity<Planet> sut = restTemplate.getForEntity("/planets/1", Planet.class);

        Assertions.assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(sut.getBody().getId()).isNotNull();
        Assertions.assertThat(sut.getBody().getName()).isEqualTo(TATOOINE.getName());
        Assertions.assertThat(sut.getBody().getClimate()).isEqualTo(TATOOINE.getClimate());
        Assertions.assertThat(sut.getBody().getTerrain()).isEqualTo(TATOOINE.getTerrain());
    }

}
