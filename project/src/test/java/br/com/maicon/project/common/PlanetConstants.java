package br.com.maicon.project.common;

import br.com.maicon.project.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name", "climate", "Terrain"); 
    public static final Planet INVALID_PLANET = new Planet("", "", ""); 
}
