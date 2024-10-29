package br.com.maicon.project.services;

import static br.com.maicon.project.common.PlanetConstants.PLANET;
import static br.com.maicon.project.common.PlanetConstants.INVALID_PLANET;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.repositories.PlanetRepository;


@ExtendWith(MockitoExtension.class)
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

}
