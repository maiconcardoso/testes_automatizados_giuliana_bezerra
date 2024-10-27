package br.com.maicon.project.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.maicon.project.domain.Planet;
import br.com.maicon.project.services.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {

    private PlanetService service;

    public PlanetController(PlanetService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Planet create(@RequestBody Planet planet) {
        return service.create(planet);
    }
}
