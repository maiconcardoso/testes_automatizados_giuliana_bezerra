package br.com.maicon.project.services;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static br.com.maicon.project.common.PlanetConstants.PLANET;
import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.repositories.PlanetRepository;


@SpringBootTest(classes = PlanetService.class)
public class PlanetServiceTest {

    @Autowired
    private PlanetService service;

    @MockBean
    private PlanetRepository repository;

    @Test
    public void createPlanet_withDataValid_returnPlanet() {
        when(repository.save(PLANET)).thenReturn(PLANET);
        Planet planet = service.create(PLANET);
        Assertions.assertThat(planet).isEqualTo(PLANET);
    }

}
