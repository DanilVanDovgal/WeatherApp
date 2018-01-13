package openWeatherMap.modelWeather;

import java.io.Serializable;

public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id;
    public String main;
    public String description;
    public String icon;
}
