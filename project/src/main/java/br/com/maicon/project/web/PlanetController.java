package br.com.maicon.project.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("{id}")
    public ResponseEntity<Planet> findById(@PathVariable Long id) {
        return service.findById(id).map((planet) -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Planet> findByName(@PathVariable String name) {
        return service.findByName(name).map((planet) -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Planet>> findAll(@RequestParam(required = false) String terrain,
            @RequestParam(required = false) String climate) {
        List<Planet> listPlanets = service.findAll(terrain, climate);
        return ResponseEntity.ok(listPlanets);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.findById(id);
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
