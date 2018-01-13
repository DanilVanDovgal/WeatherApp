package openWeatherMap.modelWeather;

import java.io.Serializable;

public class Json implements Serializable {
    private static final long serialVersionUID = 1L;
    public Coord coord;
    public Weather weather[];
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public long dt;
    public Sys sys;
    public int id;
    public String name;
    public int cod;
}
