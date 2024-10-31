package br.com.maicon.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import br.com.maicon.project.domain.Planet;

public interface PlanetRepository extends CrudRepository<Planet, Long>, QueryByExampleExecutor<Planet>{

    public Optional<Planet> findByName(String name);

    @Override
    <S extends Planet> List<S> findAll(Example<S> example);
}
