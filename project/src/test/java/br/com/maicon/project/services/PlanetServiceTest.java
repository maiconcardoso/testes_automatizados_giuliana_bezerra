package br.com.maicon.project.services;

import static br.com.maicon.project.common.PlanetConstants.INVALID_PLANET;
import static br.com.maicon.project.common.PlanetConstants.PLANET;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.repositories.PlanetRepository;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlanetServiceTest {

    @InjectMocks
    private PlanetService service;

    @Mock
    private PlanetRepository repository;

    @Test
    public void createPlanet_withDataValid_returnPlanet() {
        when(repository.save(PLANET)).thenReturn(PLANET);
        Planet sut = service.create(PLANET);
        Assertions.assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_withDataInvalid_throwsException() {
        when(repository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        Assertions.assertThatThrownBy(() -> service.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = service.findById(1l);
        Assertions.assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnEmpty() {
        when(repository.findById(1l)).thenReturn(Optional.empty());
        Optional<Planet> sut = service.findById(1l);
        Assertions.assertThat(sut).isEmpty();
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanet() {
        final String name = "name";
        when(repository.findByName(name)).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = service.findByName(name);
        Assertions.assertThat(sut).isNotEmpty();
        Assertions.assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void getPlanet_ByUnexistingName_ReturnEmpty() {
        final String name = "Unexisting Name";
        when(repository.findByName(name)).thenReturn(Optional.empty());
        Optional<Planet> sut = service.findByName(name);
        Assertions.assertThat(sut).isEmpty();
    }

   /*  @Test
    public void listPlanets_ReturnsAllPlanets() {
        List<Planet> planets = new ArrayList<>();
        planets.add(PLANET);
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(PLANET.getClimate(), PLANET.getTerrain()));
        when(repository.findAll(query)).thenReturn(planets);

        List<Planet> sut = service.findAll(PLANET.getClimate(), PLANET.getTerrain());

        Assertions.assertThat(sut).isNotEmpty();
        Assertions.assertThat(sut).hasSize(1);
        Assertions.assertThat(sut.get(0)).isEqualTo(PLANET);
    } */

    @Test
    public void listPlanets_ReturnsNoPlanets() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Planet> sut = service.findAll(PLANET.getClimate(), PLANET.getTerrain());

        Assertions.assertThat(sut).isEmpty();
    }

    @Test
    public void deletePlanet_WithExistingId_doesNotThrowAnyException() {
        Assertions.assertThatCode(() -> service.deleteById(anyLong())).doesNotThrowAnyException();
    }

    @Test
    public void deletePlanet_ByUnexistingId_throwsExceptions() {
        doThrow(new RuntimeException()).when(repository).deleteById(99l);
        Assertions.assertThatThrownBy(() -> service.deleteById(99l)).isInstanceOf(RuntimeException.class);
    }

}
