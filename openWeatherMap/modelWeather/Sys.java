package openWeatherMap.modelWeather;

import java.io.Serializable;

public class Sys implements Serializable {
    private static final long serialVersionUID = 1L;
    public int type;
    public int id;
    public double message;
    public String country;
    public long sunrise;
    public long sunset;
}
