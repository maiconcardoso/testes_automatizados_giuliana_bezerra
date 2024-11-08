package br.com.maicon.project.common;

import java.util.ArrayList;
import java.util.List;

import br.com.maicon.project.domain.Planet;

public class PlanetConstants {

    public static final Planet PLANET = new Planet("name", "climate", "Terrain"); 
    public static final Planet INVALID_PLANET = new Planet("", "", ""); 

    public static final Planet TATOOINE = new Planet("Tatooine", "arid", "desert");
    public static final Planet ALDERAAN = new Planet("Alderaan", "temparate", "grassl");
    public static final Planet VAVINIV = new Planet("Vavin IV", "Temparate", "tropical");

    public static final List<Planet> PLANETS = new ArrayList<>() {
        {
            add(TATOOINE);
            add(ALDERAAN);
            add(VAVINIV);
        }
    };

}
